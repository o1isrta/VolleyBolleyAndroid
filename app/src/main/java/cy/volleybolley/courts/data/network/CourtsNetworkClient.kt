package cy.volleybolley.courts.data.network

import cy.volleybolley.core.data.network.impl.KtorNetworkClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.http.path

class CourtsNetworkClient(
    val baseUrl: String,
) : KtorNetworkClient<CourtsRequest, CourtsResponse>() {
    override suspend fun sendRequestByType(request: CourtsRequest): HttpResponse {
        return httpClient.get(baseUrl) {
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
                httpResponse.body<CourtsResponse.GetCourtsResponse>()
            }
        }
    }
}