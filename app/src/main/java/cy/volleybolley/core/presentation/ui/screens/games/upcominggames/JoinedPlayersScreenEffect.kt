package cy.volleybolley.core.presentation.ui.screens.games.upcominggames

import cy.volleybolley.core.presentation.base.UiEffect

sealed interface JoinedPlayersScreenEffect : UiEffect {
    data object NavigateBack : JoinedPlayersScreenEffect
}
