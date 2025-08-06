package cy.volleybolley.games.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class PlayersDto(
    @SerialName("players") val players: List<Int>,
)



