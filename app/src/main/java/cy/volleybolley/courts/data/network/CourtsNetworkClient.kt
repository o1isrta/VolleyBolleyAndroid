package cy.volleybolley.courts.data.network

import cy.volleybolley.BuildConfig
import cy.volleybolley.core.data.network.impl.KtorNetworkClient
import cy.volleybolley.courts.data.dto.CourtDto
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.http.path

class CourtsNetworkClient : KtorNetworkClient<CourtsRequest, CourtsResponse>() {
    override suspend fun sendRequestByType(request: CourtsRequest): HttpResponse {
        return httpClient.get(BuildConfig.BASE_URL) {
            when (request) {
                is CourtsRequest.GetCourts -> {
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
            is CourtsRequest.GetCourts -> {
                val responseList = httpResponse.body<List<CourtDto>>()
                CourtsResponse.GetCourts(responseList)
            }
        }
    }
}
