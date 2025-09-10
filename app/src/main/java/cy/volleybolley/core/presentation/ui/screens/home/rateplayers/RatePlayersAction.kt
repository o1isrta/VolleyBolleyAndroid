package cy.volleybolley.core.presentation.ui.screens.home.rateplayers

sealed interface RatePlayersAction {
    data class RatePlayer(val playerId: Int, val rating: RatingType) : RatePlayersAction
    data object ConfirmRate : RatePlayersAction

}
