package cy.volleybolley.core.presentation.ui.screens.authorization.registration

import cy.volleybolley.auth.data.UserDto
import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.model.VolleyMocks
import cy.volleybolley.referencedata.domain.model.Country
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update

class RegistrationViewModel : BaseViewModel<RegistrationState, RegistrationEvent, RegistrationEffect>(
    initialState = RegistrationState()
) {
    override val tag = RegistrationViewModel::class.simpleName ?: ""

    init {
        uiStateMutable.update {
            it.copy(
                countryList = VolleyMocks.countries,
                selectedCountry = VolleyMocks.countries.first(),
                cityList = VolleyMocks.countries.first().cities
            )
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
            block = {
                uiStateMutable.update { it.copy(isLoading = true) }
                delay(timeMillis = 1000L)
                uiEffectMutable.send(RegistrationEffect.NavigateToHome)
                uiStateMutable.update { it.copy(isLoading = false) }
            },
            onError = {
                uiStateMutable.update { it.copy(isLoading = false) }
            },
            getErrorLogMessage = { "error in registration -> $it" }
        )
    }

    private fun isRegistrationButtonEnabled(newState: RegistrationState): Boolean {
        return newState.name.isNotBlank() && newState.surname.isNotBlank() &&
            newState.selectedCountry != null && newState.selectedCity != null &&
            newState.dateOfBirthMillis != null
    }

    fun setUser(user: UserDto) {
        uiStateMutable.update {
            it.copy(
                user = user,
                name = user.firstName,
                surname = user.lastName,
            )
        }
    }

}
