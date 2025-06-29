package cy.volleybolley.core.data.network.impl

import cy.volleybolley.core.data.network.api.NetworkClient
import cy.volleybolley.core.data.network.model.ApiRequest
import cy.volleybolley.core.data.network.model.ApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import io.ktor.http.path

/**
 * Наброски релизации NetworkClient.
 * До уточнения API реализация считается условной(!!!)
 */
class KtorNetworkClient(
    private val httpClient: HttpClient,
) : NetworkClient {

    override suspend fun getResponse(request: ApiRequest): ApiResponse {
        return runCatching {
            val httpResponse = sendValidRequestByType(request)

            if (httpResponse.status.isSuccess()) {
                httpResponse.body<ApiResponse.AuthResponse>()
            } else {
                ApiResponse.BadResponse(
                    responseStatusCode = httpResponse.status.value,
                    message = httpResponse.status.description
                )
            }

        }.onFailure { error ->
            error.printStackTrace()
        }.getOrNull() ?: ApiResponse.BadResponse()
    }

    private suspend fun sendValidRequestByType(request: ApiRequest): HttpResponse {
        return when(request) {
            is ApiRequest.ClassicAuthRequest -> {
                httpClient.request {
                    method = request.method
                    url {
                        protocol = request.protocol
                        request.host?.let { host = it }
                        request.path?.let { path(it) }

                        if (request.headers.isNotEmpty()) {
                            headers {
                                request.headers.forEach { name, value -> append(name, value) }
                            }
                        }

                        if (request.parameters.isNotEmpty()) {
                            request.parameters.forEach { key, value -> parameter(key, value) }
                        }

                        request.body?.let { setBody(it) }
                    }
                }
            }
        }
    }
}