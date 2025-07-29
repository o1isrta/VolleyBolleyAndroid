package cy.volleybolley.games.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlayerIdDto(
    @SerialName("player_id") val playerId: Int,
)
