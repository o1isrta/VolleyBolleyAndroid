package cy.volleybolley.notification.data.dto.notification

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NotificationDto(
    @SerialName("notification_id") val notificationId: Int,
    @SerialName("date") val date: String,
    @SerialName("title") val title: String,
    @SerialName("body") val body: String,
    @SerialName("screen") val screen: String?,
    @SerialName("event_id") val eventId: String?
)
