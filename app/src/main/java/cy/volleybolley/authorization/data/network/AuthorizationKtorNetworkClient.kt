package cy.volleybolley.authorization.data.network

import cy.volleybolley.BuildConfig
import cy.volleybolley.authorization.data.dto.AuthorizationRequest
import cy.volleybolley.authorization.data.dto.AuthorizationResponse
import cy.volleybolley.core.data.network.impl.KtorNetworkClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.URLProtocol
import io.ktor.http.appendPathSegments

class AuthorizationKtorNetworkClient : KtorNetworkClient<AuthorizationRequest, AuthorizationResponse>() {
    override suspend fun sendRequestByType(request: AuthorizationRequest): HttpResponse {
        return httpClient.post("\"http://158.160.175.32/api/\"") {
            /* Когда Варя ПР смержит, заменить эту строку на
            return httpClient.post(BuildConfig.BASE_URL) {
            */
            when (request) {
                is AuthorizationRequest.GoogleAuthorizationRequest-> {
                    url {
                        protocol = URLProtocol.HTTP
                        if (request.path.isNotEmpty()) appendPathSegments(request.path)
                        setBody(request.body)
                    }
                }

                is AuthorizationRequest.FacebookAuthorizationRequest -> {
                    url {
                        protocol = URLProtocol.HTTP
                        if (request.path.isNotEmpty()) appendPathSegments(request.path)
                        setBody(request.body)
                    }
                }
                is AuthorizationRequest.PhoneNumberAuthorizationRequest -> {
                    url {
                        protocol = URLProtocol.HTTP
                        if (request.path.isNotEmpty()) appendPathSegments(request.path)
                        setBody(request.body)
                    }
                }
            }
        }
    }

    override suspend fun getResponseBodyByRequestType(
        requestType: AuthorizationRequest,
        httpResponse: HttpResponse
    ): AuthorizationResponse {
        return httpResponse.body<AuthorizationResponse>()
    }
}
