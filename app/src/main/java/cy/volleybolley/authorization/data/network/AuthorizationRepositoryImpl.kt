package cy.volleybolley.authorization.data.network

import cy.volleybolley.authorization.data.dto.AuthorizationRequestBody
import cy.volleybolley.authorization.data.toAuthorizationResult
import cy.volleybolley.authorization.domain.api.AuthorizationRepository
import cy.volleybolley.authorization.domain.model.AuthorizationResult
import cy.volleybolley.core.data.network.impl.KtorNetworkClient
import cy.volleybolley.core.data.network.model.ApiRequest
import cy.volleybolley.core.data.network.model.ApiResponse

class AuthorizationRepositoryImpl(
    private val client: KtorNetworkClient
) : AuthorizationRepository {
    override suspend fun authorize(idToken: String): AuthorizationResult {
        val request = ApiRequest.AuthorizationRequest(
            body = AuthorizationRequestBody(idToken = idToken)
        )
        return (client.getResponse(request) as ApiResponse.AuthorizationResponse).toAuthorizationResult()
    }
}