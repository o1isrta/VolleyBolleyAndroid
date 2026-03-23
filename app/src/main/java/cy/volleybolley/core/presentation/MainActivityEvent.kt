package cy.volleybolley.core.presentation

import cy.volleybolley.core.presentation.base.UiEvent

sealed interface MainActivityEvent : UiEvent {
    class IntentReceived(val screen: String?, val eventId: Int?) : MainActivityEvent
    class NotificationPermissionChanged(val granted: Boolean) : MainActivityEvent
    object DismissGlobalDialog : MainActivityEvent
    object RequestPermission : MainActivityEvent
    object OnStop : MainActivityEvent
}
