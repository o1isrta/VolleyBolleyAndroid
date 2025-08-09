package cy.volleybolley.notification.data.network

import cy.volleybolley.BuildConfig
import cy.volleybolley.core.data.network.impl.KtorNetworkClient
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.path

class DeviceTokenNetworkClient : KtorNetworkClient<DeviceTokenRequest, DeviceTokenResponse>() {
    override suspend fun sendRequestByType(request: DeviceTokenRequest): HttpResponse {
        return when (request) {
            is DeviceTokenRequest.UpdateToken -> {
                httpClient.put(BuildConfig.BASE_URL) {
                    url { path(request.path) }
                    contentType(ContentType.Application.Json)
                    setBody(request.body)
                }
            }
        }
    }

    override suspend fun getResponseBodyByRequestType(
        requestType: DeviceTokenRequest,
        httpResponse: HttpResponse
    ): DeviceTokenResponse {
        return DeviceTokenResponse.Success
    }
}
