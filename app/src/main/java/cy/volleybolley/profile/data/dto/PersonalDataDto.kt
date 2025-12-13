package cy.volleybolley.profile.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonalDataDto(
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String,
    @SerialName("gender") val gender: String,
    @SerialName("level") val level: String,
    @SerialName("date_of_birth") val birthDate: String,
    @SerialName("country") val countryId: Int,
    @SerialName("city") val cityId: Int,
    @SerialName("avatar") val avatar: String?,
)
