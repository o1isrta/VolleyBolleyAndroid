package cy.volleybolley.games.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class RatePlayerDto(
    @SerialName("player_id") val playerId: Int,
    @SerialName("level_changed") val levelChanged: String,

    )
