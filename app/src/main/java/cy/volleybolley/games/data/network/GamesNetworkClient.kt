package cy.volleybolley.games.data.network

import cy.volleybolley.BuildConfig
import cy.volleybolley.core.data.network.impl.KtorNetworkClient
import cy.volleybolley.games.data.dto.GameDto
import cy.volleybolley.games.data.network.GamesResponse.*
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.path

class GamesNetworkClient() : KtorNetworkClient<GamesRequest, GamesResponse>() {
    override suspend fun sendRequestByType(request: GamesRequest): HttpResponse {
        return httpClient.post(BuildConfig.BASE_URL) {
            when (request) {
                is GamesRequest.CreateGame -> {
                    url {
                        path(request.path)
                    }
                    contentType(ContentType.Application.Json)
                    setBody(request.game)
                }

                is GamesRequest.GetGameDetails -> TODO()
            }
        }
    }

    override suspend fun getResponseBodyByRequestType(
        requestType: GamesRequest,
        httpResponse: HttpResponse
    ): GamesResponse {
        return when (requestType) {
            is GamesRequest.CreateGame -> {
                val response = httpResponse.body<GameDto>()
                CreateGame(response)
            }

            is GamesRequest.GetGameDetails -> TODO()
        }
    }
}
