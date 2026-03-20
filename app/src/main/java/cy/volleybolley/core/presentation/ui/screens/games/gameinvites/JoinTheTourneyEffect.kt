package cy.volleybolley.core.presentation.ui.screens.games.gameinvites

import cy.volleybolley.core.presentation.base.UiEffect

sealed interface JoinTheTourneyEffect : UiEffect {
    data object NavigateBack : JoinTheTourneyEffect
}
