package cy.volleybolley.courts.data.network

import android.R.attr.path
import cy.volleybolley.core.data.network.impl.KtorNetworkClient
import io.ktor.client.call.body
import io.ktor.client.engine.ProxyBuilder.http
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import io.ktor.http.path

class CourtsNetworkClient() : KtorNetworkClient<CourtsRequest, CourtsResponse>() {
    override suspend fun sendResponseByType(request: CourtsRequest): HttpResponse {
        return httpClient.get(BASE_URL) {
            when (request) {
                is CourtsRequest.GetCourtsRequest -> {
                    url {
                        path(request.path)
                        parameter("search", request.courtName)
                    }
                }
            }
        }

    }

    override suspend fun getResponseBodyByRequestType(
        requestType: CourtsRequest,
        httpResponse: HttpResponse
    ): CourtsResponse {
        return when (requestType) {
            is CourtsRequest.GetCourtsRequest -> {
                httpResponse.body()
            }
        }
    }

    companion object {
        const val BASE_URL = "http://158.160.175.32/api/"
    }
}