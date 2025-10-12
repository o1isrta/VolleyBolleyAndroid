package cy.volleybolley.core.presentation.ui.screens.games.archive.datamodel

import cy.volleybolley.courts.domain.model.Location

data class Tourney(
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
    val teams: List<ShortTeam>,
)
