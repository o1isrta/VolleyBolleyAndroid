package cy.volleybolley.notification.data.dto.notification

import kotlinx.serialization.Serializable

@Serializable
data class NotificationsResponseDto(
    val notifications: List<NotificationDto>
)
