package cy.volleybolley.notification.domain.api

interface NotificationPermissionChecker {
    fun isNotificationPermissionGranted(): Boolean
}
