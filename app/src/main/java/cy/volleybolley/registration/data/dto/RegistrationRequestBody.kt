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
    @SerialName("country") val country: Int,
    @SerialName("city") val city: Int,
    @SerialName("level") val level: String,
)

fun PersonalData.toRegistrationBody(): RegistrationRequestBody {
    return RegistrationRequestBody(
        firstName = firstName,
        lastName = lastName,
        gender = gender,
        birthDate = birthDate,
        country = countryId,
        city = cityId,
        level = level,
    )
}
