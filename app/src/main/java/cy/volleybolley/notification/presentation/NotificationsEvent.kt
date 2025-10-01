package cy.volleybolley.notification.presentation

import cy.volleybolley.core.presentation.base.UiEvent
import cy.volleybolley.notification.presentation.ui.model.NotificationItem

sealed class NotificationsEvent : UiEvent {
    object LoadNotifications : NotificationsEvent()
    data class OnNotificationClick(val notification: NotificationItem) : NotificationsEvent()
}
