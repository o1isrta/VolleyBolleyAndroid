package cy.volleybolley.rateplayers

import cy.volleybolley.core.presentation.base.UiEvent
import cy.volleybolley.games.domain.model.entity.RatingType

sealed interface RatePlayersEvent : UiEvent {
    data class RatePlayer(val index: Int, val rating: RatingType) : RatePlayersEvent
    data object ConfirmRate : RatePlayersEvent
}
