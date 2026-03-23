package cy.volleybolley.core.presentation.ui.screens.home.rateplayers

import cy.volleybolley.core.presentation.base.UiEffect

sealed interface RatePlayersEffect : UiEffect {
    data object CloseScreen : RatePlayersEffect
}
