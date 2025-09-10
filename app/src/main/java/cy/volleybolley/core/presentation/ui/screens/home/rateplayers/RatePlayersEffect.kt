package cy.volleybolley.core.presentation.ui.screens.home.rateplayers

sealed interface RatePlayersEffect {
    data class ShowError(val message: String): RatePlayersEffect
    data object CloseScreen : RatePlayersEffect
}
