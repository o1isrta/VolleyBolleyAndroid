package cy.volleybolley.core.presentation.ui.screens.home.success

import cy.volleybolley.core.presentation.base.UiEvent

sealed interface SuccessEvent: UiEvent {
    data object Done: SuccessEvent
    data class InvitePlayers(val id: Int): SuccessEvent
    data object ShareLink: SuccessEvent
}
