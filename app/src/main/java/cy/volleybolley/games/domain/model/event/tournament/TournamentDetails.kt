package cy.volleybolley.games.domain.model.event.tournament

import cy.volleybolley.courts.domain.model.Location
import cy.volleybolley.games.domain.model.entity.Host
import cy.volleybolley.games.domain.model.entity.Team

data class TournamentDetails(
    val tournamentId: Int,
    val isIndividual: Boolean,
    val tournamentType: String,
    val host: Host,
    val message: String,
    val courtLocation: Location,
    val startTime: String,
    val endTime: String,
    val gender: String,
    val levels: List<String>,
    val maximumPlayers: Int,
    val maximumTeams: Int,
    val pricePerPerson: String,
    val paymentType: String,
    val paymentAccount: String?,
    val currencyType: String,
    val teams: List<Team>,
)
