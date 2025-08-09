package cy.volleybolley.games.domain.model

import cy.volleybolley.courts.domain.model.Location

data class GamePreview(
    val gameId: Int,
    val host: Host,
    val locationDto: Location,
    val message: String,
    val startTime: String,
    val endTime: String,
)
