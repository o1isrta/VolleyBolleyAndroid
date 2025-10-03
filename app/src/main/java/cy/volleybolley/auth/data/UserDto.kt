package cy.volleybolley.auth.data

import kotlinx.serialization.SerialName

data class UserDto(
    @SerialName("player_id") val playerId: Int,
    @SerialName("is_registered") val isRegistered: Boolean,
    val avatar: String? = null,
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String,
    val gender: String,
    @SerialName("date_of_birth") val dateOfBirth: String,
    val level: String,
    val country: String,
    val city: String,
)
