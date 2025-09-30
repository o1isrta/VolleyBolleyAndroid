package cy.volleybolley.profile.presentation.ui.screens.personaldata

import cy.volleybolley.core.presentation.base.UiEvent
import cy.volleybolley.referencedata.domain.model.City
import cy.volleybolley.referencedata.domain.model.Country

sealed interface PersonalDataScreenEvent : UiEvent {
    data object OnBackFromPersonalDataClick : PersonalDataScreenEvent
    data object OnAvatarEditClick : PersonalDataScreenEvent
    data object OnUpdateButtonClick : PersonalDataScreenEvent
    data class NameChanged(val newName: String) : PersonalDataScreenEvent
    data class SurnameChanged(val newSurname: String) : PersonalDataScreenEvent
    data class GenderSelect(val genderId: Int) : PersonalDataScreenEvent
    data class DateSelect(val date: Long?) : PersonalDataScreenEvent
    data class CountrySelect(val country: Country) : PersonalDataScreenEvent
    data class CitySelect(val city: City) : PersonalDataScreenEvent
}
