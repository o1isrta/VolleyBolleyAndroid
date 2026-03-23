package cy.volleybolley.notification.data.network.notifications

import cy.volleybolley.notification.data.dto.notification.NotificationDto

sealed interface NotificationsResponse {
    class GetNotifications(
        val notifications: List<NotificationDto>
    ) : NotificationsResponse

    object MarkNotificationsAsRead : NotificationsResponse
}
