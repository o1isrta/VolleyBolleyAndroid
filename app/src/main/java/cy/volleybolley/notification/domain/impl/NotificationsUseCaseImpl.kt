package cy.volleybolley.notification.domain.impl

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.notification.domain.api.notifications.NotificationsRepository
import cy.volleybolley.notification.domain.api.notifications.NotificationsUseCase
import cy.volleybolley.notification.domain.model.Notification

class NotificationsUseCaseImpl(
    private val repository: NotificationsRepository
) : NotificationsUseCase {

    override suspend fun getNotifications(): VolleyResult<List<Notification>, ErrorType> {
        return repository.getNotifications()
    }

    override suspend fun markNotificationsAsRead(notificationIds: List<Int>): VolleyResult<Unit, ErrorType> {
        return repository.markNotificationsAsRead(notificationIds)
    }
}
