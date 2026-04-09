package cy.volleybolley.players.data.network

import cy.volleybolley.core.data.network.impl.KtorNetworkClient
import cy.volleybolley.players.data.dto.PlayerDto
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
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

            is PlayerRequest.GetPlayerDetail -> {
                httpClient.get {
                    requestConfigure(path = request.path, body = request.playerId)
                }
            }

            is PlayerRequest.AddToFavorites -> {
                httpClient.post {
                    requestConfigure(path = request.path, body = request.playerId)
                }
            }

            is PlayerRequest.RemoveFromFavorites -> {
                httpClient.delete {
                    requestConfigure(path = request.path, body = request.playerId)
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

            is PlayerRequest.GetPlayerDetail -> {
                httpResponse.body<PlayerResponse.GetPlayerDetail>()
            }

            is PlayerRequest.AddToFavorites -> PlayerResponse.AddToFavorites

            is PlayerRequest.RemoveFromFavorites -> PlayerResponse.RemoveFromFavorites
        }
    }
}
