package cy.volleybolley.core.presentation.ui.screens.games.upcominggames

import cy.volleybolley.core.presentation.base.UiEffect

sealed interface UpcomingGameDetailsEffect : UiEffect {
    data object NavigateBack : UpcomingGameDetailsEffect
}
