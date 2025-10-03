package cy.volleybolley.core.data.network.impl

import android.util.Log
import cy.volleybolley.BuildConfig
import cy.volleybolley.core.data.network.api.NetworkClient
import cy.volleybolley.core.data.network.model.Response
import cy.volleybolley.core.data.network.model.StatusCode
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.encodedPath
import io.ktor.http.isSuccess
import io.ktor.http.path
import io.ktor.http.takeFrom
import org.koin.core.component.KoinComponent
import org.koin.java.KoinJavaComponent.inject
import kotlin.coroutines.cancellation.CancellationException

abstract class KtorNetworkClient<SealedRequest, SealedResponse>(
    private val lazyHttpClient: Lazy<HttpClient> = inject(HttpClient::class.java)
) : KoinComponent, NetworkClient<SealedRequest, SealedResponse> {

    protected val httpClient: HttpClient
        get() = lazyHttpClient.value

    override suspend fun getResponse(sealedRequest: SealedRequest): Response<SealedResponse> {
        return runCatching {
            obtainResponse(
                requestType = sealedRequest,
                httpResponse = sendRequestByType(sealedRequest)
            )
        }.onFailure { error ->
            if (BuildConfig.DEBUG) {
                Log.e(NETWORK_TAG, "error in getResponse() -> $error", error)
            }

            if (error is CancellationException) {
                throw CancellationException()
            }
        }.getOrNull() ?: Response()
    }

    private suspend fun obtainResponse(
        requestType: SealedRequest,
        httpResponse: HttpResponse
    ): Response<SealedResponse> {
        if (BuildConfig.DEBUG) {
            Log.v(NETWORK_TAG, "Response body = ${httpResponse.bodyAsText()}")
        }

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

    protected fun HttpRequestBuilder.requestConfigure(path: String, accessToken: String?, body: Any? = null) {
        accessToken?.let { headers.append(HttpHeaders.Authorization, it) }
        url {
            takeFrom(BuildConfig.BASE_URL)
            val basePath = encodedPath.removeSuffix("/")   // убираем завершающий слэш
            val requestPath = path.removePrefix("/")       // убираем начальный слэш
            encodedPath = "$basePath/$requestPath"
        }
        Log.v(NETWORK_TAG, "→ FINAL URL = ${this.url.buildString()}")
        body?.let {
            contentType(ContentType.Application.Json)
            setBody(body)
        }
    }

    protected abstract suspend fun sendRequestByType(request: SealedRequest): HttpResponse

    protected abstract suspend fun getResponseBodyByRequestType(
        requestType: SealedRequest,
        httpResponse: HttpResponse
    ): SealedResponse

    companion object {
        const val NETWORK_TAG = "NETWORK_TAG"
    }
}
