package cy.volleybolley.authorization.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlayerRegistrationBody(
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String,
    val gender: String,
    @SerialName("date_of_birth") val dateOfBirth: String,
    val level: String,
    @SerialName("country_id") val countryId: Int,
    @SerialName("city_id") val cityId: Int
)
