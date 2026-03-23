package cy.volleybolley.success

import cy.volleybolley.core.presentation.base.UiEvent

sealed interface SuccessEvent : UiEvent {
    data object OnDoneClick : SuccessEvent
    data object OnInvitePlayers : SuccessEvent
    // data object OnShareLink : SuccessEvent
}
