package cy.volleybolley.profile.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonalDataUpdateBody(
    @SerialName("first_name") val firstName: String?,
    @SerialName("last_name") val lastName: String?,
    @SerialName("gender") val gender: String?,
    @SerialName("date_of_birth") val birthDate: String?,
    @SerialName("level") val level: String?,
    @SerialName("country_id") val countryId: Int?,
    @SerialName("city_id") val cityId: Int?,
)
