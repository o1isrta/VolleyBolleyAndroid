package cy.volleybolley.core.data.network.impl

import android.util.Log
import cy.volleybolley.BuildConfig
import cy.volleybolley.core.data.network.api.NetworkClient
import cy.volleybolley.core.data.network.model.Response
import cy.volleybolley.core.data.network.model.StatusCode
import io.ktor.client.HttpClient
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class KtorNetworkClient<SealedRequest, SealedResponse> : KoinComponent, NetworkClient<SealedRequest, SealedResponse> {

    protected val httpClient: HttpClient by inject()

    override suspend fun getResponse(sealedRequest: SealedRequest): Response<SealedResponse> {
        return runCatching {
            obtainResponse(
                requestType = sealedRequest,
                httpResponse = sendResponseByType(sealedRequest)
            )
        }.onFailure { error ->
            if (BuildConfig.DEBUG) {
                Log.e(NETWORK_TAG, "error in getResponse() -> $error", error)
            }
        }.getOrNull() ?: Response()
    }

    private suspend fun obtainResponse(
        requestType: SealedRequest,
        httpResponse: HttpResponse
    ): Response<SealedResponse> {
        Log.v(NETWORK_TAG, "body = ${httpResponse.bodyAsText()}")
        return if (httpResponse.status.isSuccess()) {
            Response(
                isSuccess = true,
                resultCode = StatusCode(httpResponse.status.value),
                body = getResponseBodyByRequestType(requestType, httpResponse)
            )
        } else {
            Response(
                isSuccess = false,
                resultCode = StatusCode(httpResponse.status.value)
            )
        }
    }

    protected abstract suspend fun sendResponseByType(request: SealedRequest): HttpResponse

    protected abstract suspend fun getResponseBodyByRequestType(
        requestType: SealedRequest,
        httpResponse: HttpResponse
    ): SealedResponse

    companion object {
        const val NETWORK_TAG = "NETWORK_TAG"
    }
}