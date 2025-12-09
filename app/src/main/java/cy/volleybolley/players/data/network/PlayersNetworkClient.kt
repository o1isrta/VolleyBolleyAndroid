package cy.volleybolley.players.data.network

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
                httpClient.get {
                    requestConfigure(path = request.path)
                }
            }

            is PlayerRequest.SearchPlayers -> {
                httpClient.get {
                    requestConfigure(path = request.path)
                    parameter("search", request.name)
                }
            }

            is PlayerRequest.GetPlayerDetail -> {
                httpClient.get {
                    requestConfigure(path = request.path)
                }
            }

            is PlayerRequest.AddToFavorites -> {
                httpClient.post {
                    requestConfigure(path = request.path)
                }
            }

            is PlayerRequest.RemoveFromFavorites -> {
                httpClient.delete {
                    requestConfigure(path = request.path)
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
