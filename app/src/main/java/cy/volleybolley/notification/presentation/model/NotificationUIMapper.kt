package cy.volleybolley.notification.presentation.model

import cy.volleybolley.notification.domain.model.Notification
import cy.volleybolley.notification.ui.model.NotificationItem

fun Notification.toUi(): NotificationItem {
    return NotificationItem(
        title = this.title,
        message = this.message,
        createdAt = this.createdAt,
        id = this.id,
        screen = this.screen,
    )
}
