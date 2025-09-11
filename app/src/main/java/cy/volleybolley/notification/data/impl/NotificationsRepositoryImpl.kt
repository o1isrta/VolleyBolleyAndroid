package cy.volleybolley.notification.data.impl

import cy.volleybolley.core.data.network.api.NetworkClient
import cy.volleybolley.core.data.network.model.mapToErrorType
import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.notification.data.dto.notification.toDomain
import cy.volleybolley.notification.data.network.notifications.NotificationsRequest
import cy.volleybolley.notification.data.network.notifications.NotificationsResponse
import cy.volleybolley.notification.domain.api.notifications.NotificationsRepository
import cy.volleybolley.notification.domain.model.Notification
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NotificationsRepositoryImpl(
    private val networkClient: NetworkClient<NotificationsRequest, NotificationsResponse>
) : NotificationsRepository {

    override suspend fun getNotifications(): VolleyResult<List<Notification>, ErrorType> = withContext(Dispatchers.IO) {
        val response = networkClient.getResponse(NotificationsRequest.GetNotifications())

        if (!response.isSuccess) {
            VolleyResult.Failure(response.resultCode.mapToErrorType())
        } else {
            val body = response.body as? NotificationsResponse.GetNotifications
            val notifications = body?.notifications?.map { it.toDomain() }

            notifications?.let {
                VolleyResult.Success(it)
            } ?: VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
        }
    }

    override suspend fun markNotificationsAsRead(notificationIds: List<Int>): VolleyResult<Unit, ErrorType> =
        withContext(Dispatchers.IO) {
        val response = networkClient.getResponse(
            NotificationsRequest.MarkNotificationsAsRead(notificationIds)
        )

        if (!response.isSuccess) {
            VolleyResult.Failure(response.resultCode.mapToErrorType())
        } else {
            val body = response.body as? NotificationsResponse.MarkNotificationsAsRead
            if (body != null) {
                VolleyResult.Success(Unit)
            } else {
                VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
            }
        }
    }
}
