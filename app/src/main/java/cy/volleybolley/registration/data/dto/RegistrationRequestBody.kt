package cy.volleybolley.registration.data.dto

import cy.volleybolley.profile.domain.model.PersonalData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegistrationRequestBody(
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String,
    @SerialName("gender") val gender: String,
    @SerialName("date_of_birth") val birthDate: String,
    @SerialName("level") val level: String,
    @SerialName("country_id") val countryId: Int,
    @SerialName("city_id") val cityId: Int,
)

fun PersonalData.toRegistrationBody(): RegistrationRequestBody {
    return RegistrationRequestBody(
        firstName = firstName,
        lastName = lastName,
        gender = gender,
        birthDate = birthDate,
        level = level,
        countryId = countryId,
        cityId = cityId
    )
}
