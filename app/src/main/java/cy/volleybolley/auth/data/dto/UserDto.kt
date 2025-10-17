package cy.volleybolley.auth.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName("player_id") val playerId: Int,
    @SerialName("is_registered") val isRegistered: Boolean,
    @SerialName("avatar") val avatar: String?,
    @SerialName("first_name") val firstName: String?,
    @SerialName("last_name") val lastName: String?,
    @SerialName("gender") val gender: String?,
    @SerialName("date_of_birth") val dateOfBirth: String?,
    @SerialName("level") val level: String?,
    @SerialName("country") val country: String?,
    @SerialName("city") val city: String?,
)
