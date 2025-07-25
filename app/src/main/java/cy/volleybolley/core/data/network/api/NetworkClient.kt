package cy.volleybolley.core.data.network.api

import cy.volleybolley.core.data.network.model.Response

interface NetworkClient<T, R> {
    suspend fun getResponse(sealedRequest: T): Response<R>

    companion object {
        const val TIMEOUT_MILLIS = 30_000L
    }
}