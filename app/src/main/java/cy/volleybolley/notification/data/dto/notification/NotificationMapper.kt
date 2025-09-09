package cy.volleybolley.notification.data.dto.notification

import cy.volleybolley.notification.domain.model.Notification

fun NotificationsResponseDto.toDomain(): List<Notification> {
    return notifications.map { it.toDomain() }
}

fun NotificationDto.toDomain(): Notification {
    return Notification(
        id = id,
        createdAt = createdAt,
        title = title,
        message = message,
        screen = screen
    )
}
