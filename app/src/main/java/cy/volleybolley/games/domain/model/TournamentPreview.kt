package cy.volleybolley.games.domain.model

import cy.volleybolley.courts.domain.model.Location

data class TournamentPreview(
    val tournamentId: Int,
    val host: Host,
    val location: Location,
    val message: String,
    val startTime: String,
    val endTime: String,
)
