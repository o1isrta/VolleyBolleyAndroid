package cy.volleybolley.players.data.network

import cy.volleybolley.BuildConfig
import cy.volleybolley.core.data.network.impl.KtorNetworkClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.http.path

class PlayersNetworkClient : KtorNetworkClient<PlayerRequest, PlayerResponse>() {

    override suspend fun sendRequestByType(request: PlayerRequest): HttpResponse {
        return when (request) {
            is PlayerRequest.GetAllPlayers -> {
                httpClient.get(BuildConfig.BASE_URL) {
                    url { path(request.path) }
                }
            }

            is PlayerRequest.SearchPlayers -> {
                httpClient.get(BuildConfig.BASE_URL) {
                    url {
                        path(request.path)
                        parameter("search", request.query)
                    }
                }
            }

            is PlayerRequest.GetPlayerDetail -> {
                httpClient.get(BuildConfig.BASE_URL) {
                    url { path(request.path) }
                }
            }

            is PlayerRequest.AddToFavorites -> {
                httpClient.post(BuildConfig.BASE_URL) {
                    url { path(request.path) }
                }
            }

            is PlayerRequest.RemoveFromFavorites -> {
                httpClient.delete(BuildConfig.BASE_URL) {
                    url { path(request.path) }
                }
            }
        }
    }

    override suspend fun getResponseBodyByRequestType(
        requestType: PlayerRequest,
        httpResponse: HttpResponse
    ): PlayerResponse {
        return when (requestType) {
            is PlayerRequest.GetAllPlayers -> {
                httpResponse.body<PlayerResponse.GetAllPlayers>()
            }

            is PlayerRequest.SearchPlayers -> {
                httpResponse.body<PlayerResponse.SearchPlayers>()
            }

            is PlayerRequest.GetPlayerDetail -> {
                httpResponse.body<PlayerResponse.GetPlayerDetail>()
            }

            is PlayerRequest.AddToFavorites -> {
                httpResponse.body<PlayerResponse.AddToFavorites>()
            }

            is PlayerRequest.RemoveFromFavorites -> {
                PlayerResponse.RemoveFromFavorites
            }
        }
    }
}
