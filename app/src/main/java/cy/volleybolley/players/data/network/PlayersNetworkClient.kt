package cy.volleybolley.players.data.network

import cy.volleybolley.BuildConfig
import cy.volleybolley.core.data.network.impl.KtorNetworkClient
import cy.volleybolley.players.data.dto.PlayerDto
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse

class PlayersNetworkClient : KtorNetworkClient<PlayerRequest, PlayerResponse>() {

    override suspend fun sendRequestByType(request: PlayerRequest): HttpResponse {
        return when (request) {
            is PlayerRequest.GetAllPlayers -> {
                httpClient.get(BuildConfig.BASE_URL) {
                    requestConfigure(path = request.path, accessToken = request.authToken)
                }
            }

            is PlayerRequest.SearchPlayers -> {
                httpClient.get(BuildConfig.BASE_URL) {
                    requestConfigure(path = request.path, accessToken = request.authToken)
                    parameter("search", request.name)
                }
            }

            is PlayerRequest.GetPlayerDetail -> {
                httpClient.get(BuildConfig.BASE_URL) {
                    requestConfigure(path = request.path, accessToken = request.authToken)
                }
            }

            is PlayerRequest.AddToFavorites -> {
                httpClient.post(BuildConfig.BASE_URL) {
                    requestConfigure(path = request.path, accessToken = request.authToken)
                }
            }

            is PlayerRequest.RemoveFromFavorites -> {
                httpClient.delete(BuildConfig.BASE_URL) {
                    requestConfigure(path = request.path, accessToken = request.authToken)
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
                val list = httpResponse.body<List<PlayerDto>>()
                PlayerResponse.GetAllPlayers(list)
            }

            is PlayerRequest.SearchPlayers -> {
                val list = httpResponse.body<List<PlayerDto>>()
                PlayerResponse.SearchPlayers(list)
            }

            is PlayerRequest.GetPlayerDetail -> {
                httpResponse.body<PlayerResponse.GetPlayerDetail>()
            }

            is PlayerRequest.AddToFavorites -> {
                val dto = httpResponse.body<PlayerDto>()
                PlayerResponse.AddToFavorites(dto)
            }

            is PlayerRequest.RemoveFromFavorites -> {
                PlayerResponse.RemoveFromFavorites
            }
        }
    }
}
