package cy.volleybolley.rateplayers

import cy.volleybolley.core.presentation.base.UiEffect

sealed interface RatePlayersEffect : UiEffect {
    data object CloseScreen : RatePlayersEffect
}
