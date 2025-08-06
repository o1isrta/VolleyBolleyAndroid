package cy.volleybolley.games.data.network

import cy.volleybolley.BuildConfig
import cy.volleybolley.core.data.network.impl.KtorNetworkClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.path

class GamesNetworkClient() : KtorNetworkClient<GamesRequest, GamesResponse>() {
    override suspend fun sendRequestByType(request: GamesRequest): HttpResponse {
        return when (request) {
            is GamesRequest.CreateGame -> httpClient.post(BuildConfig.BASE_URL) {
                url {
                    path(request.path)
                }
                contentType(ContentType.Application.Json)
                setBody(request.game)
            }

            is GamesRequest.CreateTournament -> httpClient.post(BuildConfig.BASE_URL) {
                url {
                    path(request.path)
                }
                contentType(ContentType.Application.Json)
                setBody(request.tournament)
            }

            is GamesRequest.GetGameDetails -> httpClient.get(BuildConfig.BASE_URL) {
                url {
                    path(request.fullPath())
                }
            }

            is GamesRequest.GetTournamentDetails -> httpClient.get(BuildConfig.BASE_URL) {
                url {
                    path(request.fullPath())
                }
            }


            is GamesRequest.InvitePlayersToGame -> httpClient.post(BuildConfig.BASE_URL) {
                url {
                    path(request.fullPath())
                }
                contentType(ContentType.Application.Json)
                setBody(request.players)
            }

            is GamesRequest.InvitePlayersToTournament -> httpClient.post(BuildConfig.BASE_URL) {
                url {
                    path(request.fullPath())
                }
                contentType(ContentType.Application.Json)
                setBody(request.players)
            }

            is GamesRequest.GetPreview -> httpClient.get(BuildConfig.BASE_URL) {
                url {
                    path(request.fullPath())
                }
            }

            is GamesRequest.GetMyGames -> httpClient.get(BuildConfig.BASE_URL) {
                url {
                    path((request.fullPath()))
                }
            }

            is GamesRequest.GetInvites -> httpClient.get(BuildConfig.BASE_URL) {
                url {
                    path((request.fullPath()))
                }
            }

            is GamesRequest.GetArchive -> httpClient.get(BuildConfig.BASE_URL) {
                url {
                    path((request.fullPath()))
                }
            }

            is GamesRequest.GetUpcoming -> httpClient.get(BuildConfig.BASE_URL) {
                url {
                    path((request.fullPath()))
                }
            }


            is GamesRequest.JoinGame -> TODO()
            is GamesRequest.JoinTournament -> TODO()
            is GamesRequest.GetPlayersToRate -> TODO()
            is GamesRequest.RatePlayers -> TODO()
            is GamesRequest.SkipRating -> TODO()
        }

    }

    override suspend fun getResponseBodyByRequestType(
        requestType: GamesRequest,
        httpResponse: HttpResponse
    ): GamesResponse {
        return when (requestType) {
            is GamesRequest.CreateGame -> {
                httpResponse.body<GamesResponse.CreateGame>()
            }

            is GamesRequest.CreateTournament -> {
                httpResponse.body<GamesResponse.CreateTournament>()
            }

            is GamesRequest.GetGameDetails -> {
                httpResponse.body<GamesResponse.GetGameDetails>()
            }

            is GamesRequest.GetTournamentDetails -> {
                httpResponse.body<GamesResponse.GetTournamentDetails>()
            }

            is GamesRequest.InvitePlayersToGame -> {
                httpResponse.body<GamesResponse.InvitePlayersToGame>()
            }

            is GamesRequest.InvitePlayersToTournament -> {
                httpResponse.body<GamesResponse.InvitePlayersToTournament>()
            }

            is GamesRequest.GetPreview -> {
                httpResponse.body<GamesResponse.GetPreview>()
            }

            is GamesRequest.GetMyGames -> {
                httpResponse.body<GamesResponse.GetMyGames>()
            }

            is GamesRequest.GetArchive -> {
                httpResponse.body<GamesResponse.GetArchive>()
            }

            is GamesRequest.GetInvites -> {
                httpResponse.body<GamesResponse.GetInvites>()
            }

            is GamesRequest.GetUpcoming -> {
                httpResponse.body<GamesResponse.GetUpcoming>()
            }

            is GamesRequest.GetPlayersToRate -> TODO()
            is GamesRequest.JoinGame -> TODO()
            is GamesRequest.JoinTournament -> TODO()
            is GamesRequest.RatePlayers -> TODO()
            is GamesRequest.SkipRating -> TODO()
        }
    }
}
