package cy.volleybolley.notification.domain.api.permission

interface NotificationPermissionChecker {
    fun isNotificationPermissionGranted(): Boolean
}
