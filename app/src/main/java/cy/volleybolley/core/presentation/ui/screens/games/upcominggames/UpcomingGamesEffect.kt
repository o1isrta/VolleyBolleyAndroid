package cy.volleybolley.core.presentation.ui.screens.games.upcominggames

import cy.volleybolley.core.presentation.base.UiEffect

sealed interface UpcomingGamesEffect : UiEffect {
    data object NavigateBack : UpcomingGamesEffect
}
