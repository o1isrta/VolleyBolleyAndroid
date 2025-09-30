package cy.volleybolley.notification.presentation.model

import cy.volleybolley.notification.domain.model.Notification
import cy.volleybolley.notification.presentation.ui.model.NotificationItem

fun Notification.toUi(): NotificationItem {
    return NotificationItem(
        title = title,
        body = message,
        date = date,
        notificationId = notificationId,
        screen = screen,
        eventId = eventId
    )
}
