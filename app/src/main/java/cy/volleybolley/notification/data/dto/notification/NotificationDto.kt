package cy.volleybolley.notification.data.dto.notification

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NotificationDto(
    @SerialName("notification_id") val id: Int,
    @SerialName("created_at") val createdAt: String,
    @SerialName("title") val title: String,
    @SerialName("message") val message: String,
    @SerialName("screen") val screen: String
)
