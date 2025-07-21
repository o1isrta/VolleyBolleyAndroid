package cy.volleybolley.core.data.network.api

import cy.volleybolley.core.data.network.model.ApiRequest
import cy.volleybolley.core.data.network.model.ApiResponse

interface NetworkClient {
    suspend fun getResponse(request: ApiRequest): ApiResponse

    companion object {
        const val TIMEOUT_MILLIS = 30_000L
    }
}