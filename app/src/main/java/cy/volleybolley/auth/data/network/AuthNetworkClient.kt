package cy.volleybolley.auth.data.network

import cy.volleybolley.auth.data.network.model.AuthRequest
import cy.volleybolley.auth.data.network.model.AuthResponse
import cy.volleybolley.core.data.network.impl.KtorNetworkClient
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse

class AuthNetworkClient(
    lazyHttpClient: Lazy<HttpClient>
) : KtorNetworkClient<AuthRequest, AuthResponse>(lazyHttpClient) {
    override suspend fun sendRequestByType(request: AuthRequest): HttpResponse {
        return when (request) {
            is AuthRequest.Google -> {
                httpClient.post {
                    requestConfigure(path = AuthRequest.Google.PATH, body = request.body)
                }
            }

            is AuthRequest.RefreshAccessToken -> {
                httpClient.post {
                    requestConfigure(path = AuthRequest.RefreshAccessToken.PATH, body = request.body)
                }
            }

            is AuthRequest.Phone -> {
                httpClient.post {
                    requestConfigure(path = AuthRequest.Phone.PATH, body = request.body)
                }
            }
        }
    }

    override suspend fun getResponseBodyByRequestType(
        requestType: AuthRequest,
        httpResponse: HttpResponse
    ): AuthResponse {
        return when (requestType) {
            is AuthRequest.Google -> {
                val googleResponse = httpResponse.body<AuthResponse.GoogleResponse>()
                googleResponse
            }

            is AuthRequest.RefreshAccessToken -> {
                val refreshResponse = httpResponse.body<AuthResponse.RefreshAccessTokenResponse>()
                refreshResponse
            }

            is AuthRequest.Phone -> {
                val phoneResponse = httpResponse.body<AuthResponse.GoogleResponse>()
                phoneResponse
            }
        }
    }
}
