package cy.volleybolley.notification.presentation.model

import cy.volleybolley.notification.domain.model.Notification
import cy.volleybolley.notification.presentation.ui.model.NotificationItem

fun Notification.toUi(): NotificationItem {
    return NotificationItem(
        title = title,
        message = message,
        createdAt = createdAt,
        id = id,
        screen = screen,
        gameId = gameId
    )
}
