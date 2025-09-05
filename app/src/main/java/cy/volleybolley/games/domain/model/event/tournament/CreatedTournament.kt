package cy.volleybolley.games.domain.model.event.tournament

import cy.volleybolley.games.domain.model.entity.ShortTeam

data class CreatedTournament(
    val tournamentId: Int,
    val courtId: Int,
    val message: String,
    val startTime: String,
    val endTime: String,
    val isIndividual: Boolean,
    val gender: String,
    val levels: List<String>,
    val maximumPlayers: Int,
    val maximumTeams: Int,
    val pricePerPerson: String,
    val paymentType: String,
    val paymentAccount: String?,
    val currencyType: String,
    val teams: List<ShortTeam>,
)
