package cy.volleybolley.notification.data.network.notifications

import cy.volleybolley.BuildConfig
import cy.volleybolley.core.data.network.impl.KtorNetworkClient
import cy.volleybolley.notification.data.dto.notification.NotificationReadDto
import cy.volleybolley.notification.data.dto.notification.NotificationsResponseDto
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.path

class NotificationsNetworkClient : KtorNetworkClient<NotificationsRequest, NotificationsResponse>() {
    override suspend fun sendRequestByType(request: NotificationsRequest): HttpResponse {
        return when (request) {
            is NotificationsRequest.GetNotifications -> {
                httpClient.get(BuildConfig.BASE_URL) {
                    url { path(request.path) }
                }
            }

            is NotificationsRequest.MarkNotificationsAsRead -> {
                httpClient.patch(BuildConfig.BASE_URL) {
                    url { path(request.path) }
                    contentType(ContentType.Application.Json)
                    setBody(
                        mapOf(
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
