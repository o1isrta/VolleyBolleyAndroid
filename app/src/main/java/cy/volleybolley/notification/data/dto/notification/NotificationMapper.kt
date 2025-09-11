package cy.volleybolley.notification.data.dto.notification

import cy.volleybolley.notification.domain.model.Notification

fun NotificationDto.toDomain(): Notification {
    return Notification(
        id = id,
        createdAt = createdAt,
        title = title,
        message = message,
        screen = screen,
        gameId = gameId
    )
}
