package cy.volleybolley.games.data.network

import cy.volleybolley.BuildConfig
import cy.volleybolley.core.data.network.impl.KtorNetworkClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.path

class GamesNetworkClient : KtorNetworkClient<GamesRequest, GamesResponse>() {
    override suspend fun sendRequestByType(request: GamesRequest): HttpResponse {
        return when (request) {
            is GamesRequest.CreateGame -> httpClient.post(BuildConfig.BASE_URL) {
                url {
                    path(request.path)
                }
                contentType(ContentType.Application.Json)
                setBody(request.game)
            }

            is GamesRequest.GetGameDetails -> httpClient.get(BuildConfig.BASE_URL) {
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

            is GamesRequest.GetPreview -> httpClient.get(BuildConfig.BASE_URL) {
                url {
                    path(request.fullPath())
                }
            }

            is GamesRequest.GetMyGames -> httpClient.get(BuildConfig.BASE_URL) {
                url {
                    path(request.fullPath())
                }
            }

            is GamesRequest.GetInvites -> httpClient.get(BuildConfig.BASE_URL) {
                url {
                    path(request.fullPath())
                }
            }

            is GamesRequest.GetArchive -> httpClient.get(BuildConfig.BASE_URL) {
                url {
                    path(request.fullPath())
                }
            }

            is GamesRequest.GetUpcoming -> httpClient.get(BuildConfig.BASE_URL) {
                url {
                    path(request.fullPath())
                }
            }

            is GamesRequest.JoinGame -> httpClient.post(BuildConfig.BASE_URL) {
                url {
                    path(request.fullPath())
                }
            }

            is GamesRequest.DeclineGameInvite -> httpClient.delete(BuildConfig.BASE_URL) {
                url {
                    path(request.fullPath())
                }
            }

            is GamesRequest.CancelGame -> httpClient.post(BuildConfig.BASE_URL) {
                url {
                    path(request.fullPath())
                }
            }

            is GamesRequest.GetPlayersToRate -> httpClient.get(BuildConfig.BASE_URL) {
                url {
                    path(request.fullPath())
                }
            }

            is GamesRequest.RatePlayers -> httpClient.post(BuildConfig.BASE_URL) {
                url {
                    path(request.fullPath())
                }
                contentType(ContentType.Application.Json)
                setBody(request.players)
            }

            is GamesRequest.SkipRating -> httpClient.post(BuildConfig.BASE_URL) {
                url {
                    path(request.fullPath())
                }
            }
        }

    }

    override suspend fun getResponseBodyByRequestType(
        requestType: GamesRequest, httpResponse: HttpResponse
    ): GamesResponse {
        return when (requestType) {
            is GamesRequest.CreateGame -> {
                httpResponse.body<GamesResponse.CreateGame>()
            }

            is GamesRequest.GetGameDetails -> {
                httpResponse.body<GamesResponse.GetGameDetails>()
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

            is GamesRequest.JoinGame -> {
                httpResponse.body<GamesResponse.JoinGame>()
            }

            is GamesRequest.DeclineGameInvite -> {
                httpResponse.body<GamesResponse.DeclineGameInvite>()
            }

            is GamesRequest.GetPlayersToRate -> {
                httpResponse.body<GamesResponse.GetPlayersToRate>()
            }

            is GamesRequest.RatePlayers -> {
                httpResponse.body<GamesResponse.RatePlayers>()
            }

            is GamesRequest.SkipRating -> {
                httpResponse.body<GamesResponse.SkipRating>()
            }

            is GamesRequest.InvitePlayersToGame -> {
                httpResponse.body<GamesResponse.InvitePlayersToGame>()
            }

            is GamesRequest.CancelGame -> {
                httpResponse.body<GamesResponse.CancelGame>()
            }
        }
    }
}
