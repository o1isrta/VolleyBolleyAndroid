package cy.volleybolley.notification.presentation.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import cy.volleybolley.R
import cy.volleybolley.core.presentation.MainActivity
import cy.volleybolley.notification.domain.api.storages.FCMTokenStore
import cy.volleybolley.notification.presentation.ui.model.NotificationItem
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinComponent

class FirebaseNotificationService : FirebaseMessagingService(), KoinComponent {
    private val fcmTokenStore: FCMTokenStore by inject()
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM_TOKEN", token)
        fcmTokenStore.saveToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val data = remoteMessage.data
        val title = remoteMessage.notification?.title ?: "FCM"
        val body = remoteMessage.notification?.body ?: ""
        val notificationItem = NotificationItem(
            notificationId = data[KEY_NOTIFICATION_ID]?.toIntOrNull() ?: 0,
            date = data[KEY_DATE] ?: "",
            title = title,
            body = body,
            screen = data[KEY_SCREEN],
            eventId = data[KEY_EVENT_ID]
        )
        showNotification(notificationItem)
    }

    private fun showNotification(item: NotificationItem) {
        val channelId = CHANNEL_ID

        val intent = Intent(this, MainActivity::class.java).apply {
            item.screen?.let { putExtra(KEY_SCREEN, it) }
            item.eventId?.let { putExtra(KEY_EVENT_ID, it) }
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_notification)
            .setContentTitle(item.title)
            .setContentText(item.body)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                getString(R.string.default_channel_name),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(item.notificationId, builder.build())
    }

    companion object {
        private const val CHANNEL_ID = "default_channel"
        private const val KEY_NOTIFICATION_ID = "notification_id"
        private const val KEY_DATE = "date"
        private const val KEY_SCREEN = "screen"
        private const val KEY_EVENT_ID = "event_id"
    }
}
