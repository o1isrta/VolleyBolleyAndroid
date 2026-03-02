package cy.volleybolley.core.presentation

import cy.volleybolley.core.presentation.base.UiEvent

sealed class MainActivityEvent : UiEvent {
    data class IntentReceived(val screen: String?, val eventId: Int?) : MainActivityEvent()
    data class TokenFetchFailed(val exception: Exception?) : MainActivityEvent()
    data class NotificationPermissionChanged(val granted: Boolean) : MainActivityEvent()
    object DismissGlobalDialog : MainActivityEvent()
    object RequestPermission : MainActivityEvent()
}
