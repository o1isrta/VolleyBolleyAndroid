package cy.volleybolley.notification.presentation.ui.model

data class NotificationItem(
    val notificationId: Int,
    val date: String,
    val title: String,
    val body: String,
    val screen: String?,
    val eventId: String?,
)
