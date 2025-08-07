package cy.volleybolley.core.data.network.impl

import cy.volleybolley.core.data.network.model.PlayerDto
import cy.volleybolley.core.data.network.model.PlayerRequest
import cy.volleybolley.core.data.network.model.PlayerResponse
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.delete
import io.ktor.http.path
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import org.koin.core.component.inject

private const val PLAYERS_PATH = "players"
private const val FAVORITE_PATH = "favorite"

class PlayersNetworkClient : KtorNetworkClient<PlayerRequest, PlayerResponse>() {

    private val json: Json by inject()

    override suspend fun sendRequestByType(request: PlayerRequest): HttpResponse {
        return when (request) {
            is PlayerRequest.GetAllPlayers -> {
                httpClient.get { url { path(PLAYERS_PATH) } }
            }
            is PlayerRequest.SearchPlayers -> {
                httpClient.get {
                    url {
                        path(PLAYERS_PATH)
                        parameters.append("search", request.query)
                    }
                }
            }
            is PlayerRequest.GetPlayerDetail -> {
                httpClient.get { url { path(PLAYERS_PATH, request.playerId.toString()) } }
            }
            is PlayerRequest.AddToFavorites -> {
                httpClient.post { url { path(PLAYERS_PATH, request.playerId.toString(), FAVORITE_PATH) } }
            }
            is PlayerRequest.RemoveFromFavorites -> {
                httpClient.delete { url { path(PLAYERS_PATH, request.playerId.toString(), FAVORITE_PATH) } }
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
