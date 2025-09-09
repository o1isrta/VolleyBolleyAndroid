package cy.volleybolley.notification.domain.model

data class Notification(
    val id: Int,
    val createdAt: String,
    val title: String,
    val message: String,
    val screen: String
)
