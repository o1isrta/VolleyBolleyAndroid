package cy.volleybolley.core.presentation.ui.screens.home.rateplayers

sealed interface RatePlayersEvent {
    data class RatePlayer(val playerId: Int, val rating: RatingType) : RatePlayersEvent
    data object ConfirmRate : RatePlayersEvent

}
