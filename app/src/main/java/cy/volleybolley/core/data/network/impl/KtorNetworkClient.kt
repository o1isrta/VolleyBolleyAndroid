package cy.volleybolley.core.data.network.impl

import cy.volleybolley.BuildConfig
import cy.volleybolley.core.data.network.api.NetworkClient
import cy.volleybolley.core.data.network.model.Response
import cy.volleybolley.core.data.network.model.StatusCode
import cy.volleybolley.core.util.VolleyLog
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.encodedPath
import io.ktor.http.isSuccess
import io.ktor.http.takeFrom
import okio.IOException
import org.koin.core.component.KoinComponent
import org.koin.java.KoinJavaComponent.inject
import kotlin.coroutines.cancellation.CancellationException

abstract class KtorNetworkClient<SealedRequest, SealedResponse>(
    private val lazyHttpClient: Lazy<HttpClient> = inject(HttpClient::class.java)
) : KoinComponent, NetworkClient<SealedRequest, SealedResponse> {

    protected val httpClient: HttpClient get() = lazyHttpClient.value

    override suspend fun getResponse(sealedRequest: SealedRequest): Response<SealedResponse> {
        return runCatching {
            obtainResponse(
                requestType = sealedRequest,
                httpResponse = sendRequestByType(sealedRequest)
            )
        }.onFailure { error ->
            when (error) {
                is IOException -> return Response(resultCode = StatusCode(StatusCode.NO_CONNECTION))
                is CancellationException -> throw CancellationException()
                else -> VolleyLog.e(TAG, "error in getResponse() -> $error", error)
            }
        }.getOrNull() ?: Response()
    }

    private suspend fun obtainResponse(
        requestType: SealedRequest,
        httpResponse: HttpResponse
    ): Response<SealedResponse> {
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

    protected fun HttpRequestBuilder.requestConfigure(path: String, body: Any? = null) {
        url {
            takeFrom(BuildConfig.BASE_URL)
            val basePath = encodedPath.removeSuffix("/")
            encodedPath = "$basePath$path"
        }
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

    private companion object {
        val TAG = KtorNetworkClient::class.simpleName.orEmpty()
    }
}
