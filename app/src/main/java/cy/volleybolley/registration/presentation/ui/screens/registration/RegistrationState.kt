package cy.volleybolley.registration.presentation.ui.screens.registration

import cy.volleybolley.core.domain.model.LevelType
import cy.volleybolley.core.presentation.base.UiState
import cy.volleybolley.core.presentation.ui.model.VolleyUiUtil
import cy.volleybolley.profile.domain.model.PersonalData
import cy.volleybolley.profile.presentation.ui.screens.personaldata.model.GenderType
import cy.volleybolley.referencedata.domain.model.City
import cy.volleybolley.referencedata.domain.model.Country

data class RegistrationState(
    val name: String = "",
    val surname: String = "",
    val gender: Int = 1,
    val level: Int = 1,
    val dateOfBirthMillis: Long? = null,
    val country: String = "",
    val selectedCountry: Country? = null,
    val countryList: List<Country> = emptyList(),
    val selectedCity: City? = null,
    val cityList: List<City> = emptyList(),
    val isBtnRegistrationEnabled: Boolean = false,
    val isLoading: Boolean = false
) : UiState

fun RegistrationState.toPersonalData(): PersonalData {
    return PersonalData(
        firstName = name,
        lastName = surname,
        gender = GenderType.getNameValueById(gender),
        birthDate = dateOfBirthMillis?.let {
            VolleyUiUtil.convertMillisToTextDate(
                VolleyUiUtil.DATE_OF_BIRTH_PATTERN_FOR_SERVER,
                it
            )
        } ?: "2000-12-31",
        level = LevelType.getLevelNameById(level),
        countryId = selectedCountry?.id ?: -1,
        cityId = selectedCity?.id ?: -1,
        avatar = null
    )
}
