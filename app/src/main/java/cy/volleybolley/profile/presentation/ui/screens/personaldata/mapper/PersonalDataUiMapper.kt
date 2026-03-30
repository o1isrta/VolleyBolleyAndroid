package cy.volleybolley.profile.presentation.ui.screens.personaldata.mapper

import cy.volleybolley.core.presentation.ui.model.VolleyUiUtil
import cy.volleybolley.profile.domain.model.PersonalData
import cy.volleybolley.profile.presentation.ui.screens.personaldata.PersonalDataScreenState
import cy.volleybolley.profile.presentation.ui.screens.personaldata.model.GenderType
import cy.volleybolley.referencedata.domain.model.Country

fun PersonalData?.withCountriesToState(
    countries: List<Country>? = null,
    stateForButtonEnabledFlag: PersonalDataScreenState,
): PersonalDataScreenState {
    val selectedCountryById = countries?.find { it.id == this?.countryId }
    return PersonalDataScreenState(
        avatar = this?.avatar,
        name = this?.firstName ?: "",
        surname = this?.lastName ?: "",
        levelHolder = this?.level ?: "",
        genderId = this?.gender?.let {
            GenderType.getIdByStringValue(it)
        } ?: 0,
        dateOfBirthMillis = this?.birthDate?.let {
            VolleyUiUtil.convertTextDateToMillis(
                VolleyUiUtil.DATE_OF_BIRTH_PATTERN_FOR_SERVER,
                it
            )
        },
        selectedCountry = selectedCountryById,
        selectedCity = selectedCountryById?.cities?.find { it.id == this?.cityId },
        countryList = countries ?: emptyList(),
        cityList = selectedCountryById?.cities ?: emptyList(),
        buttonEnabled = stateForButtonEnabledFlag.buttonEnabled
    )
}

private fun PersonalDataScreenState.toPersonalData(): PersonalData {
    return PersonalData(
        firstName = name,
        lastName = surname,
        gender = GenderType.getNameValueById(genderId),
        birthDate = dateOfBirthMillis?.let {
            VolleyUiUtil.convertMillisToTextDate(
                VolleyUiUtil.DATE_OF_BIRTH_PATTERN_FOR_SERVER,
                it
            )
        } ?: "1999-12-31",
        level = levelHolder,
        countryId = selectedCountry?.id ?: -1,
        cityId = selectedCity?.id ?: -1,
        avatar = avatar
    )
}
