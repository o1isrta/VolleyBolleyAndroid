package cy.volleybolley.core.presentation.ui.screens.authorization.registration

import androidx.lifecycle.viewModelScope
import cy.volleybolley.core.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class RegistrationViewModel :
    BaseViewModel<RegistrationState, RegistrationEvent, RegistrationEffect>(
        RegistrationState()
    ) {

    override val tag: String = "RegistrationViewModel"

    override fun obtainEvent(event: RegistrationEvent) {
        when (event) {
            RegistrationEvent.AboutLevelsClicked,
            RegistrationEvent.GetStartedClicked -> handleNavigationEvent(event)

            is RegistrationEvent.NameChanged,
            is RegistrationEvent.SurnameChanged,
            is RegistrationEvent.GenderSelected,
            is RegistrationEvent.LevelSelected,
            is RegistrationEvent.DateOfBirthChanged,
            is RegistrationEvent.CountryChanged,
            is RegistrationEvent.CityChanged -> handleStateEvent(event)
        }
    }

    private fun handleNavigationEvent(event: RegistrationEvent) {
        when (event) {
            RegistrationEvent.AboutLevelsClicked -> viewModelScope.launch {
                sendUiEffect(RegistrationEffect.NavigateToAboutLevels)
            }

            RegistrationEvent.GetStartedClicked -> viewModelScope.launch {
                sendUiEffect(RegistrationEffect.NavigateToHome)
            }

            else -> {
            }
        }
    }

    private fun handleStateEvent(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.NameChanged ->
                uiStateMutable.value = uiStateMutable.value.copy(name = event.value)

            is RegistrationEvent.SurnameChanged ->
                uiStateMutable.value = uiStateMutable.value.copy(surname = event.value)

            is RegistrationEvent.GenderSelected ->
                uiStateMutable.value = uiStateMutable.value.copy(gender = event.id)

            is RegistrationEvent.LevelSelected ->
                uiStateMutable.value = uiStateMutable.value.copy(level = event.id)

            is RegistrationEvent.DateOfBirthChanged ->
                uiStateMutable.value =
                    uiStateMutable.value.copy(dateOfBirthMillis = event.millis)

            is RegistrationEvent.CountryChanged ->
                uiStateMutable.value = uiStateMutable.value.copy(country = event.value)

            is RegistrationEvent.CityChanged ->
                uiStateMutable.value = uiStateMutable.value.copy(city = event.value)

            else -> {
            }
        }
    }
}
