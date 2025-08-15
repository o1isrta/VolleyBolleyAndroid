package cy.volleybolley.authorization.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlayerDto(
    @SerialName("player_id") val playerId: Int,
    val avatar: String? = null,
    @SerialName("first_name") val firstName: String? = null,
    @SerialName("last_name") val lastName: String? = null,
    val gender: String? = null,
    @SerialName("date_of_birth") val dateOfBirth: String? = null,
    val level: String? = null,
    @SerialName("country_id") val countryId: Int? = null,
    @SerialName("city_id") val cityId: Int? = null
)
