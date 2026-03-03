package cy.volleybolley.core.presentation.ui.screens.games.gameinvites

import cy.volleybolley.core.presentation.base.UiEffect

sealed interface GameInvitesScreenEffect : UiEffect {
    data object NavigateBack : GameInvitesScreenEffect
}
