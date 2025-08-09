package cy.volleybolley.games.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class PlayerShortDto(
    @SerialName("player_id") val playerId: Int,
    @SerialName("first_name") val firstName: String?,
    @SerialName("last_name") val lastName: String?,
    @SerialName("level") val level: String?,
    @SerialName("avatar") val avatar: String?,
)
