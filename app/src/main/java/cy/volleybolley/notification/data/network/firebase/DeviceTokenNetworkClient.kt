package cy.volleybolley.notification.data.network.firebase

import cy.volleybolley.BuildConfig
import cy.volleybolley.core.data.network.impl.KtorNetworkClient
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpMethod

class DeviceTokenNetworkClient : KtorNetworkClient<DeviceTokenRequest, DeviceTokenResponse>() {
    override suspend fun sendRequestByType(request: DeviceTokenRequest): HttpResponse {
        return httpClient.request(urlString = BuildConfig.BASE_URL) {
            when (request) {
                is DeviceTokenRequest.UpdateToken -> {
                    method = HttpMethod.Put
                    requestConfigure(
                        path = request.path,
                        body = request.body
                    )
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
