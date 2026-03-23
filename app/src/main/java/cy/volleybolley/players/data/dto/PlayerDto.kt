package cy.volleybolley.players.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlayerDto(
    @SerialName("player_id") val id: Int,
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String,
    @SerialName("avatar") val avatarUrl: String? = null,
    @SerialName("is_favorite") val isFavorite: Boolean,
    @SerialName("level") val level: String,
    @SerialName("gender") val gender: String
)
