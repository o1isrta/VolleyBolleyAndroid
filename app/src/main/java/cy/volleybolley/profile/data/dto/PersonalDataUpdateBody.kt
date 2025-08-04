package cy.volleybolley.profile.data.dto

import cy.volleybolley.profile.domain.model.PersonalData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class PersonalDataUpdateBody(
    @SerialName("first_name") val firstName: String?,
    @SerialName("last_name") val lastName: String?,
    @SerialName("gender") val gender: String?,
    @SerialName("date_of_birth") val birthDate: String?,
    @SerialName("level") val level: String?,
    @SerialName("country_id") val countryId: Int?,
    @SerialName("city_id") val cityId: Int?,
)

fun PersonalData.toUpdateBody(): PersonalDataUpdateBody {
    return PersonalDataUpdateBody(
        firstName = checkStringDataField(firstName),
        lastName = checkStringDataField(lastName),
        gender = checkStringDataField(gender),
        birthDate = checkStringDataField(birthDate),
        level = checkStringDataField(level),
        countryId = checkIntDataField(countryId),
        cityId = checkIntDataField(cityId)
    )
}

private fun checkStringDataField(field: String): String? = if (field.isEmpty()) null else field
private fun checkIntDataField(field: Int): Int? = if (field == -1) null else field
