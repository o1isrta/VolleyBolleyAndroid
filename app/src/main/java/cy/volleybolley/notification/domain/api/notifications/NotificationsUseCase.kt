package cy.volleybolley.notification.domain.api.notifications

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.notification.domain.model.Notification

interface NotificationsUseCase {
    suspend fun getNotifications(): VolleyResult<List<Notification>, ErrorType>
    suspend fun markNotificationsAsRead(notificationIds: List<Int>): VolleyResult<Unit, ErrorType>
}
