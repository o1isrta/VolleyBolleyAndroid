package cy.volleybolley.notification.data

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import cy.volleybolley.notification.domain.api.NotificationPermissionChecker

class NotificationPermissionCheckerImpl(
    private val context: Context
) : NotificationPermissionChecker {

    override fun isNotificationPermissionGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }
}
