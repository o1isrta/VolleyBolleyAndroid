package cy.volleybolley.profile.data.dto

import cy.volleybolley.profile.domain.model.PersonalData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class PersonalDataDto(
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String,
    @SerialName("gender") val gender: String,
    @SerialName("date_of_birth") val birthDate: String,
    @SerialName("level") val level: String,
    @SerialName("country") val country: String,
    @SerialName("city") val city: String,
    @SerialName("avatar") val avatar: String,
)

fun PersonalDataDto.toDomain(): PersonalData {
    return PersonalData(
        firstName = firstName,
        lastName = lastName,
        gender = gender,
        birthDate = birthDate,
        level = level,
        country = country,
        city = city,
        avatar = avatar,
    )
}
