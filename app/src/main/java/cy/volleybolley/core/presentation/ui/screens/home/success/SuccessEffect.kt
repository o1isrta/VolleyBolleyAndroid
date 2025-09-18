package cy.volleybolley.core.presentation.ui.screens.home.success

import cy.volleybolley.core.presentation.base.UiEffect

sealed interface SuccessEffect: UiEffect {
    data object CloseScreen: SuccessEffect
    data object NavigateToInvitePlayers: SuccessEffect
    data object ShareLink: SuccessEffect
}
