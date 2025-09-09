package cy.volleybolley.notification.presentation.model

import cy.volleybolley.notification.ui.model.NotificationItem

sealed class NotificationsEvent {
    object LoadNotifications : NotificationsEvent()
    data class OnNotificationClick(val notification: NotificationItem) : NotificationsEvent()
}
