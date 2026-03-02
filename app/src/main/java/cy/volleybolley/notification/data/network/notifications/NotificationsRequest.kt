package cy.volleybolley.notification.data.network.notifications

sealed interface NotificationsRequest {
    val accessToken: String?
        get() = null

    class GetNotifications(
        val path: String = NOTIFICATIONS,
    ) : NotificationsRequest

    class MarkNotificationsAsRead(
        val notificationIds: List<Int>,
        val path: String = NOTIFICATIONS
    ) : NotificationsRequest

    companion object {
        private const val NOTIFICATIONS = "/notifications"
    }
}
