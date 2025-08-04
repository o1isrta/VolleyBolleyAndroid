package cy.volleybolley.core.data.network.impl

import cy.volleybolley.core.data.network.model.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import org.koin.core.component.inject

class PlayersNetworkClient : KtorNetworkClient<PlayerRequest, PlayerResponse>() {

    private val json: Json by inject()

    override suspend fun sendRequestByType(request: PlayerRequest): HttpResponse {
        return when (request) {
            is PlayerRequest.GetAllPlayers -> {
                httpClient.get { url { path("players") } }
            }
            is PlayerRequest.SearchPlayers -> {
                httpClient.get {
                    url {
                        path("players")
                        parameters.append("search", request.query)
                    }
                }
            }
            is PlayerRequest.GetPlayerDetail -> {
                httpClient.get { url { path("players", request.playerId.toString()) } }
            }
            is PlayerRequest.AddToFavorites -> {
                httpClient.post { url { path("players", request.playerId.toString(), "favorite") } }
            }
            is PlayerRequest.RemoveFromFavorites -> {
                httpClient.delete { url { path("players", request.playerId.toString(), "favorite") } }
            }
        }
    }

    override suspend fun getResponseBodyByRequestType(
        requestType: PlayerRequest,
        httpResponse: HttpResponse
    ): PlayerResponse {
        val bodyText = httpResponse.bodyAsText()

        return when (requestType) {
            is PlayerRequest.GetAllPlayers,
            is PlayerRequest.SearchPlayers -> {
                val list = json.decodeFromString(ListSerializer(PlayerDto.serializer()), bodyText)
                PlayerResponse.PlayerList(list)
            }
            is PlayerRequest.GetPlayerDetail -> {
                val detail = json.decodeFromString(PlayerResponse.PlayerDetail.serializer(), bodyText)
                detail
            }
            is PlayerRequest.AddToFavorites,
            is PlayerRequest.RemoveFromFavorites -> {
                if (bodyText.isNotBlank()) {
                    val dto = json.decodeFromString(PlayerDto.serializer(), bodyText)
                    PlayerResponse.PlayerList(listOf(dto))
                } else {
                    PlayerResponse.Empty
                }
            }
        }
    }
}
