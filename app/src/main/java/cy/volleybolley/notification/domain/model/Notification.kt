package cy.volleybolley.notification.domain.model

data class Notification(
    val notificationId: Int,
    val date: String,
    val title: String,
    val message: String,
    val screen: String?,
    val eventId: String?,
)
