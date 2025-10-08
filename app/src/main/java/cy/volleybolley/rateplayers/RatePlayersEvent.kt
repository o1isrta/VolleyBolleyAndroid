package cy.volleybolley.rateplayers

import cy.volleybolley.core.presentation.base.UiEvent

sealed interface RatePlayersEvent : UiEvent {
    data class RatePlayer(val playerId: Int, val rating: RatingType) : RatePlayersEvent
    data object ConfirmRate : RatePlayersEvent
}
