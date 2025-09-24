package cy.volleybolley.core.presentation.ui.screens.authorization.registration

import cy.volleybolley.core.presentation.base.UiEvent
import cy.volleybolley.referencedata.domain.model.City
import cy.volleybolley.referencedata.domain.model.Country

sealed class RegistrationEvent : UiEvent {
    object GetStartedClicked : RegistrationEvent()
    data class NameChanged(val value: String) : RegistrationEvent()
    data class SurnameChanged(val value: String) : RegistrationEvent()
    data class GenderSelected(val id: Int) : RegistrationEvent()
    data class LevelSelected(val id: Int) : RegistrationEvent()
    data class DateOfBirthChanged(val millis: Long?) : RegistrationEvent()
    data class CountrySelected(val value: Country) : RegistrationEvent()
    data class CitySelected(val value: City) : RegistrationEvent()
}
