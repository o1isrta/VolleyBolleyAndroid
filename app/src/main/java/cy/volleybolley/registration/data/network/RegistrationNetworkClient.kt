package cy.volleybolley.registration.data.network

import cy.volleybolley.core.data.network.impl.KtorNetworkClient
import cy.volleybolley.registration.data.network.model.RegistrationRequest
import cy.volleybolley.registration.data.network.model.RegistrationResponse
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpMethod

class RegistrationNetworkClient : KtorNetworkClient<RegistrationRequest, RegistrationResponse>() {
    override suspend fun sendRequestByType(request: RegistrationRequest): HttpResponse {
        return httpClient.request {
            when (request) {
                is RegistrationRequest.UserRegister -> {
                    method = HttpMethod.Post
                    requestConfigure(path = request.path, body = request.body)
                }
            }
        }
    }

    override suspend fun getResponseBodyByRequestType(
        requestType: RegistrationRequest,
        httpResponse: HttpResponse
    ): RegistrationResponse {
        return when (requestType) {
            is RegistrationRequest.UserRegister -> RegistrationResponse.UserRegister
        }
    }
}
