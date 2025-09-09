package cy.volleybolley.notification.data.network.notifications

sealed interface NotificationsRequest {
    class GetNotifications(
        val path: String = "/notifications",
    ) : NotificationsRequest

    class MarkNotificationsAsRead(
        val notificationIds: List<Int>,
        val path: String = "/notifications"
    ) : NotificationsRequest
}
