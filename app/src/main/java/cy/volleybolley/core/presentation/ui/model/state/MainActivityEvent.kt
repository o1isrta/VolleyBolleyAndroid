package cy.volleybolley.core.presentation.ui.model.state

sealed interface MainActivityEvent {
    data class IntentReceived(val screen: String?, val gameId: String?) : MainActivityEvent
    data class TokenFetchFailed(val exception: Exception?) : MainActivityEvent
    data class NotificationPermissionChanged(val granted: Boolean) : MainActivityEvent
}
