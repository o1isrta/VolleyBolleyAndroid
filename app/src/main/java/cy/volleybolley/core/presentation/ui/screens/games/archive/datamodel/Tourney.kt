package cy.volleybolley.core.presentation.ui.screens.games.archive.datamodel

data class Tourney(
    val tourneyHost: PlayerShort,
    val hostMessage: String,
    val location: String,
    val timeAndDate: String,
    val level: String,
    val gender: String,
    val paymentMethodType: String,
    val paymentMethod: String,
    val feePerPerson: String,
    val isIndividual: Boolean,
    val players: List<PlayerShort>,
    val teams: List<Team>
)
