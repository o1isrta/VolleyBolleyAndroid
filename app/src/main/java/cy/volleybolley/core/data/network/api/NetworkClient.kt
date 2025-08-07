package cy.volleybolley.core.data.network.api

import cy.volleybolley.core.data.network.model.Response

interface NetworkClient<SealedRequest, SealedResponse> {
    suspend fun getResponse(sealedRequest: SealedRequest): Response<SealedResponse>
}
