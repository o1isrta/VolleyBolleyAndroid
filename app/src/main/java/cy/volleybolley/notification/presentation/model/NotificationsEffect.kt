package cy.volleybolley.notification.presentation.model

sealed class NotificationsEffect {
    data class ShowError(val message: String) : NotificationsEffect()
    data class NavigateTo(val screen: String) : NotificationsEffect()
}
