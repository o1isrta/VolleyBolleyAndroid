package cy.volleybolley.profile.presentation.ui.screens.personaldata

import cy.volleybolley.core.presentation.base.UiState
import cy.volleybolley.referencedata.domain.model.City
import cy.volleybolley.referencedata.domain.model.Country

data class PersonalDataScreenState(
    val avatar: String? = null,
    val name: String = "",
    val surname: String = "",
    val levelHolder: String = "",
    val genderId: Int = 0,
    val dateOfBirthMillis: Long? = null,
    val selectedCountry: Country? = null,
    val selectedCity: City? = null,
    val countryList: List<Country> = emptyList(),
    val cityList: List<City> = emptyList(),
    val buttonEnabled: Boolean = false,
    val isLoading: Boolean = false,
) : UiState

fun PersonalDataScreenState.isPersonalDataNotEmpty(): Boolean {
    return name.isNotBlank() &&
        surname.isNotBlank() &&
        dateOfBirthMillis != null &&
        selectedCountry != null &&
        selectedCity != null
}

fun PersonalDataScreenState.isPersonalDataChanged(other: PersonalDataScreenState): Boolean {
    return name != other.name || surname != other.surname ||
        dateOfBirthMillis != other.dateOfBirthMillis || selectedCountry != other.selectedCountry ||
        selectedCity != other.selectedCity
}
