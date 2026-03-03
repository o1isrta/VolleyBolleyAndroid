package cy.volleybolley.core.presentation.ui.screens.games.upcominggames

import cy.volleybolley.core.presentation.base.UiEffect

sealed interface UpcomingGamesScreenEffect : UiEffect {
    data object NavigateBack : UpcomingGamesScreenEffect
}
