package cy.volleybolley.notification.data.dto.notification

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NotificationDto(
    val id: Int,
    @SerialName("created_at") val createdAt: String,
    val title: String,
    val message: String,
    val screen: String
)
