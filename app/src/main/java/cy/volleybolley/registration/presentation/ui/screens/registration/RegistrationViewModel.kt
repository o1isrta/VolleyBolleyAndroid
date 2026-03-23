package cy.volleybolley.registration.presentation.ui.screens.registration

import cy.volleybolley.auth.domain.api.usecase.GetPersonalDataUseCase
import cy.volleybolley.core.domain.model.onFailure
import cy.volleybolley.core.domain.model.onSuccess
import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.util.VolleyLog
import cy.volleybolley.referencedata.domain.api.GetCountriesUseCase
import cy.volleybolley.referencedata.domain.model.Country
import cy.volleybolley.registration.domain.UserRegistrationUseCase
import cy.volleybolley.registration.presentation.ui.screens.registration.RegistrationEffect.NavigateToHome
import cy.volleybolley.registration.presentation.ui.screens.registration.RegistrationEffect.ShowToast
import cy.volleybolley.registration.presentation.ui.screens.registration.RegistrationEvent.CitySelected
import cy.volleybolley.registration.presentation.ui.screens.registration.RegistrationEvent.CountrySelected
import cy.volleybolley.registration.presentation.ui.screens.registration.RegistrationEvent.DateOfBirthChanged
import cy.volleybolley.registration.presentation.ui.screens.registration.RegistrationEvent.GenderSelected
import cy.volleybolley.registration.presentation.ui.screens.registration.RegistrationEvent.GetStartedClicked
import cy.volleybolley.registration.presentation.ui.screens.registration.RegistrationEvent.LevelSelected
import cy.volleybolley.registration.presentation.ui.screens.registration.RegistrationEvent.NameChanged
import cy.volleybolley.registration.presentation.ui.screens.registration.RegistrationEvent.SurnameChanged
import kotlinx.coroutines.flow.update

class RegistrationViewModel(
    private val getCountriesUseCase: GetCountriesUseCase,
    private val userRegistrationUseCase: UserRegistrationUseCase,
    private val getPersonalDataUseCase: GetPersonalDataUseCase,
) : BaseViewModel<RegistrationState, RegistrationEvent, RegistrationEffect>(
    initialState = RegistrationState()
) {
    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        launchSafe(
            onError = { throwable ->
                sendUiEffect(ShowToast("Failed to load countries"))
            },
            getErrorLogMessage = { throwable ->
                "Error loading countries: ${throwable.message}"
            }
        ) {
            val personalData = getPersonalDataUseCase.execute()
            getCountriesUseCase.execute()
                .onSuccess { countries ->
                    VolleyLog.v(tag, "RegistrationScreen >> GetCountries = $countries")
                    uiStateMutable.update {
                        it.copy(
                            name = personalData?.firstName ?: "",
                            surname = personalData?.lastName ?: "",
                            countryList = countries,
                            selectedCountry = countries.firstOrNull(),
                            cityList = countries.firstOrNull()?.cities ?: emptyList()
                        )
                    }
                }
                .onFailure { error ->
                    VolleyLog.e(tag, "error in load initial data -> $error")
                    sendUiEffect(ShowToast("Failed to load countries"))
                    uiStateMutable.update {
                        it.copy(
                            name = personalData?.firstName ?: "",
                            surname = personalData?.lastName ?: ""
                        )
                    }
                }
        }
    }

    override fun obtainEvent(event: RegistrationEvent) {
        when (event) {
            is NameChanged -> {
                uiStateMutable.update {
                    val newState = it.copy(name = event.value)
                    newState.copy(isBtnRegistrationEnabled = isRegistrationButtonEnabled(newState))
                }
            }

            is SurnameChanged -> {
                uiStateMutable.update {
                    val newState = it.copy(surname = event.value)
                    newState.copy(isBtnRegistrationEnabled = isRegistrationButtonEnabled(newState))
                }
            }

            is GenderSelected -> {
                uiStateMutable.update { it.copy(gender = event.id) }
            }

            is LevelSelected -> {
                uiStateMutable.update { it.copy(level = event.id) }
            }

            is DateOfBirthChanged -> {
                uiStateMutable.update {
                    val newState = it.copy(dateOfBirthMillis = event.millis)
                    newState.copy(isBtnRegistrationEnabled = isRegistrationButtonEnabled(newState))
                }
            }

            is CountrySelected -> onCountrySelected(event.value)

            is CitySelected -> {
                uiStateMutable.update {
                    val newState = it.copy(selectedCity = event.value)
                    newState.copy(isBtnRegistrationEnabled = isRegistrationButtonEnabled(newState))
                }
            }

            is GetStartedClicked -> sendRegistrationRequest()
        }
    }

    private fun onCountrySelected(country: Country) {
        uiStateMutable.update {
            val newState = it.copy(
                selectedCountry = country,
                selectedCity = null,
                cityList = country.cities
            )
            newState.copy(isBtnRegistrationEnabled = isRegistrationButtonEnabled(newState))
        }
    }

    private fun sendRegistrationRequest() {
        launchSafe(
            onError = { throwable ->
                uiStateMutable.update { it.copy(isLoading = false) }
                sendUiEffect(ShowToast("Registration failed: ${throwable.message}"))
            },
            getErrorLogMessage = { throwable ->
                "Error in registration: ${throwable.message}"
            }
        ) {
            uiStateMutable.update { it.copy(isLoading = true) }

            val personalData = uiState.value.toPersonalData()
            userRegistrationUseCase.execute(personalData)
                .onSuccess {
                    uiStateMutable.update { it.copy(isLoading = false) }
                    sendUiEffect(NavigateToHome)
                }
                .onFailure { error ->
                    uiStateMutable.update { it.copy(isLoading = false) }
                    sendUiEffect(ShowToast("Registration failed: $error"))
                }
        }
    }

    private fun isRegistrationButtonEnabled(newState: RegistrationState): Boolean {
        return newState.name.isNotBlank() && newState.surname.isNotBlank() &&
            newState.selectedCountry != null && newState.selectedCity != null &&
            newState.dateOfBirthMillis != null
    }
}
