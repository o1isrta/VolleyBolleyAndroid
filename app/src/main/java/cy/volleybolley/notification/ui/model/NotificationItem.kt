package cy.volleybolley.notification.ui.model

data class NotificationItem(
    val id: Int,
    val createdAt: String,
    val title: String,
    val message: String,
    val screen: String
)
