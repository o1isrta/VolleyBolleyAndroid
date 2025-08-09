package cy.volleybolley.games.domain.model

import cy.volleybolley.courts.domain.model.Location

data class TournamentDetails(
    val tournamentId: Int,
    val gameType: String,
    val host: Host,
    val message: String,
    val courtLocation: Location,
    val startTime: String,
    val endTime: String,
    val levels: List<String>,
    val gender: String,
    val pricePerPerson: String,
    val currencyType: String,
    val paymentType: String,
    val paymentAccount: String,
    val maximumPlayers: Int,
    val maximumTeams: Int,
    val players: List<Team>,
)
