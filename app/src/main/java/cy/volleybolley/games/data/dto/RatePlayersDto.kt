package cy.volleybolley.games.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class RatePlayersDto(
    @SerialName("players") val players: List<RatePlayerDto>,
)
