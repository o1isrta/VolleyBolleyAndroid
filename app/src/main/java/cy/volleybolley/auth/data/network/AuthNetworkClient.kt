package cy.volleybolley.auth.data.network

import cy.volleybolley.BuildConfig
import cy.volleybolley.auth.data.network.model.AuthRequest
import cy.volleybolley.auth.data.network.model.AuthResponse
import cy.volleybolley.core.data.network.impl.KtorNetworkClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse

class AuthNetworkClient : KtorNetworkClient<AuthRequest, AuthResponse>() {
    override suspend fun sendRequestByType(request: AuthRequest): HttpResponse {
        return when (request) {
            is AuthRequest.Google -> {
                httpClient.post(urlString = BuildConfig.BASE_URL) {
                    requestConfigure(
                        path = request.path,
                        accessToken = null,
                        body = request.body
                    )
                }
            }
            is AuthRequest.RefreshAccessToken -> {
                httpClient.post(urlString = BuildConfig.BASE_URL) {
                    requestConfigure(
                        path = request.path,
                        accessToken = null,
                        body = request.body
                    )
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
        }
    }
}
