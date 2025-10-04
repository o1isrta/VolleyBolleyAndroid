package cy.volleybolley.auth.data

import cy.volleybolley.core.data.network.impl.KtorNetworkClient
import cy.volleybolley.core.data.network.model.Response
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse

class AuthNetworkClient : KtorNetworkClient<AuthRequestType, AuthResponse>() {
    companion object {
        private const val PATH = "/auth/google/login/"
    }

    override suspend fun sendRequestByType(request: AuthRequestType): HttpResponse {
        return when (request) {
            is AuthRequestType.Google -> {
                httpClient.post {
                    requestConfigure(
                        path = PATH,
                        accessToken = null,
                        body = request.request
                    )
                }
            }
        }
    }

    override suspend fun getResponse(sealedRequest: AuthRequestType): Response<AuthResponse> {
        return super.getResponse(sealedRequest)
    }

    override suspend fun getResponseBodyByRequestType(
        requestType: AuthRequestType,
        httpResponse: HttpResponse
    ): AuthResponse {
        return httpResponse.body()
    }
}
