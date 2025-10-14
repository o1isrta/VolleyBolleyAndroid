package cy.volleybolley.games.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class TeamDto(
    @SerialName("team_id") val teamId: Int? = null,
    @SerialName("players") val players: List<PlayerShortDto>,
)
