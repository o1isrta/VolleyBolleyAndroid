package cy.volleybolley.core.presentation.ui.screens.authorization.registration

import cy.volleybolley.auth.domain.api.usecase.SavePersonalDataUseCase
import cy.volleybolley.core.domain.model.onFailure
import cy.volleybolley.core.domain.model.onSuccess
import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.model.VolleyUiUtil.showDebugLog
import cy.volleybolley.profile.domain.model.PersonalData
import cy.volleybolley.referencedata.domain.api.GetCountriesUseCase
import cy.volleybolley.referencedata.domain.model.Country
import cy.volleybolley.registration.domain.UserRegistrationUseCase
import kotlinx.coroutines.flow.update
import kotlinx.serialization.json.Json

class RegistrationViewModel(
    private val getCountriesUseCase: GetCountriesUseCase,
    private val userRegistrationUseCase: UserRegistrationUseCase,
    private val savePersonalDataUseCase: SavePersonalDataUseCase,
    json: Json,
    userData: String,
) : BaseViewModel<RegistrationState, RegistrationEvent, RegistrationEffect>(
    initialState = RegistrationState()
) {
    override val tag = RegistrationViewModel::class.simpleName ?: "RegistrationViewModel"

    init {
        val personalData: PersonalData = json.decodeFromString(userData)

        // Get countries
        launchSafe(
            onError = { throwable ->
                sendUiEffect(RegistrationEffect.ShowToast("Failed to load countries"))
            },
            getErrorLogMessage = { throwable ->
                "Error loading countries: ${throwable.message}"
            }
        ) {
            getCountriesUseCase.execute()
                .onSuccess { countries ->
                    showDebugLog(tag, "RegistrationScreen >> GetCountries = $countries")
                    uiStateMutable.update {
                        it.copy(
                            name = personalData.firstName,
                            surname = personalData.lastName,
                            countryList = countries,
                            selectedCountry = countries.firstOrNull(),
                            cityList = countries.firstOrNull()?.cities ?: emptyList()
                        )
                    }
                }
                .onFailure { error ->
                    sendUiEffect(RegistrationEffect.ShowToast("Failed to load countries"))
                    uiStateMutable.update {
                        it.copy(
                            name = personalData.firstName,
                            surname = personalData.lastName
                        )
                    }
                }
        }
    }

    override fun obtainEvent(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.NameChanged -> {
                uiStateMutable.update {
                    val newState = it.copy(name = event.value)
                    newState.copy(isBtnRegistrationEnabled = isRegistrationButtonEnabled(newState))
                }
            }

            is RegistrationEvent.SurnameChanged -> {
                uiStateMutable.update {
                    val newState = it.copy(surname = event.value)
                    newState.copy(isBtnRegistrationEnabled = isRegistrationButtonEnabled(newState))
                }
            }

            is RegistrationEvent.GenderSelected -> {
                uiStateMutable.update { it.copy(gender = event.id) }
            }

            is RegistrationEvent.LevelSelected -> {
                uiStateMutable.update { it.copy(level = event.id) }
            }

            is RegistrationEvent.DateOfBirthChanged -> {
                uiStateMutable.update {
                    val newState = it.copy(dateOfBirthMillis = event.millis)
                    newState.copy(isBtnRegistrationEnabled = isRegistrationButtonEnabled(newState))
                }
            }

            is RegistrationEvent.CountrySelected -> onCountrySelected(event.value)

            is RegistrationEvent.CitySelected -> {
                uiStateMutable.update {
                    val newState = it.copy(selectedCity = event.value)
                    newState.copy(isBtnRegistrationEnabled = isRegistrationButtonEnabled(newState))
                }
            }

            is RegistrationEvent.GetStartedClicked -> sendRegistrationRequest()
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
                sendUiEffect(RegistrationEffect.ShowToast("Registration failed: ${throwable.message}"))
            },
            getErrorLogMessage = { throwable ->
                "Error in registration: ${throwable.message}"
            }
        ) {
            uiStateMutable.update { it.copy(isLoading = true) }

            val personalData = uiState.value.toPersonalData()
            showDebugLog(tag, "RegistrationScreen >> UserData for registration = $personalData")
            userRegistrationUseCase.execute(personalData)
                .onSuccess {
                    savePersonalDataUseCase.execute(personalData)
                    uiStateMutable.update { it.copy(isLoading = false) }
                    sendUiEffect(RegistrationEffect.NavigateToHome)
                }
                .onFailure { error ->
                    uiStateMutable.update { it.copy(isLoading = false) }
                    sendUiEffect(RegistrationEffect.ShowToast("Registration failed: $error"))
                }
        }
    }

    private fun isRegistrationButtonEnabled(newState: RegistrationState): Boolean {
        return newState.name.isNotBlank() && newState.surname.isNotBlank() &&
            newState.selectedCountry != null && newState.selectedCity != null &&
            newState.dateOfBirthMillis != null
    }
}
