package cy.volleybolley.notification.data.dto.notification

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NotificationReadDto(
    @SerialName("notification_id") val notificationId: Int
)
