package cy.volleybolley.notification.data.network.notifications

import cy.volleybolley.BuildConfig
import cy.volleybolley.core.data.network.impl.KtorNetworkClient
import cy.volleybolley.notification.data.dto.notification.NotificationReadDto
import cy.volleybolley.notification.data.dto.notification.NotificationsResponseDto
import io.ktor.client.call.body
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpMethod

class NotificationsNetworkClient : KtorNetworkClient<NotificationsRequest, NotificationsResponse>() {
    override suspend fun sendRequestByType(request: NotificationsRequest): HttpResponse {
        return httpClient.request(urlString = BuildConfig.BASE_URL) {
            when (request) {
                is NotificationsRequest.GetNotifications -> {
                    method = HttpMethod.Get
                    requestConfigure(
                        path = request.path
                    )
                }

                is NotificationsRequest.MarkNotificationsAsRead -> {
                    method = HttpMethod.Patch
                    requestConfigure(
                        path = request.path,
                        body = mapOf(
                            "notifications" to request.notificationIds.map {
                                NotificationReadDto(notificationId = it)
                            }
                        )
                    )
                }
            }
        }
    }

    override suspend fun getResponseBodyByRequestType(
        requestType: NotificationsRequest,
        httpResponse: HttpResponse
    ): NotificationsResponse {
        return when (requestType) {
            is NotificationsRequest.GetNotifications -> {
                val response = httpResponse.body<NotificationsResponseDto>()
                NotificationsResponse.GetNotifications(response.notifications)
            }

            is NotificationsRequest.MarkNotificationsAsRead -> NotificationsResponse.MarkNotificationsAsRead
        }
    }
}
