package cy.volleybolley.registration.presentation.ui.screens.registration

import cy.volleybolley.core.presentation.base.UiEvent
import cy.volleybolley.referencedata.domain.model.City
import cy.volleybolley.referencedata.domain.model.Country

sealed interface RegistrationEvent : UiEvent {
    object GetStartedClicked : RegistrationEvent
    class NameChanged(val value: String) : RegistrationEvent
    class SurnameChanged(val value: String) : RegistrationEvent
    class GenderSelected(val id: Int) : RegistrationEvent
    class LevelSelected(val id: Int) : RegistrationEvent
    class DateOfBirthChanged(val millis: Long?) : RegistrationEvent
    class CountrySelected(val value: Country) : RegistrationEvent
    class CitySelected(val value: City) : RegistrationEvent
}
