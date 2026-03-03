package cy.volleybolley.core.presentation.ui.screens.games.archive.teamsscreen.effect

import cy.volleybolley.core.presentation.base.UiEffect

sealed interface TeamsScreenEffect : UiEffect {
    data object NavigateBack : TeamsScreenEffect
}
