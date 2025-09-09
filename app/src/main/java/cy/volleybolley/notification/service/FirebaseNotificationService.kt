package cy.volleybolley.notification.service

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
        val title = remoteMessage.notification?.title ?: "FCM"
        val message = remoteMessage.notification?.body ?: ""
        val screen = remoteMessage.data["screen"]
        val gameId = remoteMessage.data["gameId"]
        showNotification(
            title = title,
            message = message,
            screen = screen,
            gameId = gameId
        )
    }

    private fun showNotification(
        title: String,
        message: String,
        screen: String? = null,
        gameId: String? = null
    ) {
        val channelId = "default_channel"

        val intent = Intent(this, MainActivity::class.java).apply {
            screen?.let { putExtra("screen", it) }
            gameId?.let { putExtra("gameId", it) }
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
            .setContentTitle(title)
            .setContentText(message)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Default Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
        val notificationId = System.currentTimeMillis().toInt()
        notificationManager.notify(notificationId, builder.build())
    }
}
