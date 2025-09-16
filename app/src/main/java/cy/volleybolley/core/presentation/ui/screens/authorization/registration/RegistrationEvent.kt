package cy.volleybolley.core.presentation.ui.screens.authorization.registration

import cy.volleybolley.core.presentation.base.UiEvent

sealed class RegistrationEvent : UiEvent {
    object AboutLevelsClicked : RegistrationEvent()
    object GetStartedClicked : RegistrationEvent()
    data class NameChanged(val value: String) : RegistrationEvent()
    data class SurnameChanged(val value: String) : RegistrationEvent()
    data class GenderSelected(val id: Int) : RegistrationEvent()
    data class LevelSelected(val id: Int) : RegistrationEvent()
    data class DateOfBirthChanged(val millis: Long?) : RegistrationEvent()
    data class CountryChanged(val value: String) : RegistrationEvent()
    data class CityChanged(val value: String) : RegistrationEvent()
}
