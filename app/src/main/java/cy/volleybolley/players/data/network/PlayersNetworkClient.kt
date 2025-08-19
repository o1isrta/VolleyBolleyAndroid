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
import org.koin.core.component.inject

interface AccessTokenProvider {
    suspend fun getAccessToken(): String?
}

class PlayersNetworkClient : KtorNetworkClient<PlayerRequest, PlayerResponse>() {

    private val tokenProvider: AccessTokenProvider by inject()

    override suspend fun sendRequestByType(request: PlayerRequest): HttpResponse {
        val token = tokenProvider.getAccessToken()

        return when (request) {
            is PlayerRequest.GetAllPlayers -> {
                httpClient.get(BuildConfig.BASE_URL) {
                    requestConfigure(path = request.path, accessToken = token)
                }
            }

            is PlayerRequest.SearchPlayers -> {
                httpClient.get(BuildConfig.BASE_URL) {
                    requestConfigure(path = request.path, accessToken = token)
                    parameter("search", request.name)
                }
            }

            is PlayerRequest.GetPlayerDetail -> {
                httpClient.get(BuildConfig.BASE_URL) {
                    requestConfigure(path = request.path, accessToken = token)
                }
            }

            is PlayerRequest.AddToFavorites -> {
                httpClient.post(BuildConfig.BASE_URL) {
                    requestConfigure(path = request.path, accessToken = token)
                }
            }

            is PlayerRequest.RemoveFromFavorites -> {
                httpClient.delete(BuildConfig.BASE_URL) {
                    requestConfigure(path = request.path, accessToken = token)
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
                val players = httpResponse.body<List<PlayerDto>>()
                PlayerResponse.GetAllPlayers(players)
            }

            is PlayerRequest.SearchPlayers -> {
                val players = httpResponse.body<List<PlayerDto>>()
                PlayerResponse.SearchPlayers(players)
            }

            is PlayerRequest.GetPlayerDetail -> {
                httpResponse.body<PlayerResponse.GetPlayerDetail>()
            }

            is PlayerRequest.AddToFavorites -> {
                val player = httpResponse.body<PlayerDto>()
                PlayerResponse.AddToFavorites(player)
            }

            is PlayerRequest.RemoveFromFavorites -> {
                PlayerResponse.RemoveFromFavorites
            }
        }
    }
}
