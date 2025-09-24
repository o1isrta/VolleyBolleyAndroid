package cy.volleybolley.core.presentation.ui.screens.authorization.registration

import androidx.lifecycle.viewModelScope
import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.referencedata.domain.model.City
import cy.volleybolley.referencedata.domain.model.Country
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegistrationViewModel : BaseViewModel<RegistrationState, RegistrationEvent, RegistrationEffect>(
    initialState = RegistrationState()
) {
    override val tag = RegistrationViewModel::class.simpleName ?: ""

    init {
        uiStateMutable.update {
            val countyList = listOf(Country(
                id = 0,
                name = "Thailand",
                cities = listOf(
                    City(id = 0, name = "Koh Phangan"),
                    City(id = 1, name = "Koh Samui")
                )
            ))
            it.copy(
                countryList = countyList,
                selectedCountry = countyList.first(),
                cityList = countyList.first().cities
            )
        }
    }

    override fun obtainEvent(event: RegistrationEvent) {
        when (event) {
            RegistrationEvent.GetStartedClicked -> handleNavigationEvent(event)

            is RegistrationEvent.NameChanged,
            is RegistrationEvent.SurnameChanged,
            is RegistrationEvent.GenderSelected,
            is RegistrationEvent.LevelSelected,
            is RegistrationEvent.DateOfBirthChanged,
            is RegistrationEvent.CountrySelected,
            is RegistrationEvent.CitySelected -> handleStateEvent(event)
        }
    }

    private fun handleNavigationEvent(event: RegistrationEvent) {
        when (event) {
            RegistrationEvent.GetStartedClicked -> viewModelScope.launch {
                sendUiEffect(RegistrationEffect.NavigateToHome)
            }

            else -> {
            }
        }
    }

    private fun handleStateEvent(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.NameChanged -> {
                uiStateMutable.update { it.copy(name = event.value) }
            }

            is RegistrationEvent.SurnameChanged -> {
                uiStateMutable.update { it.copy(surname = event.value) }
            }

            is RegistrationEvent.GenderSelected -> {
                uiStateMutable.update { it.copy(gender = event.id) }
            }

            is RegistrationEvent.LevelSelected -> {
                uiStateMutable.update { it.copy(level = event.id) }
            }

            is RegistrationEvent.DateOfBirthChanged -> {
                uiStateMutable.update { it.copy(dateOfBirthMillis = event.millis) }
            }

            is RegistrationEvent.CountrySelected -> {
                uiStateMutable.update {
                    it.copy(
                        selectedCountry = event.value,
                        cityList = event.value.cities
                    )
                }
            }

            is RegistrationEvent.CitySelected -> {
                uiStateMutable.update { it.copy(selectedCity = event.value) }
            }

            is RegistrationEvent.GetStartedClicked -> {
                // empty
            }
        }
    }
}
