package cy.volleybolley.notification.data.dto.notification

import cy.volleybolley.notification.domain.model.Notification

fun NotificationDto.toDomain(): Notification {
    return Notification(
        notificationId = notificationId,
        date = date,
        title = title,
        message = body,
        screen = screen,
        eventId = eventId
    )
}
