package cy.volleybolley.games.data.network

import cy.volleybolley.core.data.network.impl.KtorNetworkClient
import io.ktor.client.call.body
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpMethod

@Suppress("LongMethod", "ComplexMethod")
class GamesNetworkClient : KtorNetworkClient<GamesRequest, GamesResponse>() {
    override suspend fun sendRequestByType(request: GamesRequest): HttpResponse {
        return httpClient.request {
            when (request) {
                is GamesRequest.CreateGame -> {
                    method = HttpMethod.Post
                    println("Sending CreateGame request to: ${request.path}")
                    println("Body: ${request.game}")

                    requestConfigure(request.path, body = request.game)
                }

                is GamesRequest.GetGameDetails -> {
                    method = HttpMethod.Get
                    requestConfigure(request.fullPath())
                }

                is GamesRequest.InvitePlayersToGame -> {
                    method = HttpMethod.Post
                    requestConfigure(request.fullPath(), body = request.players)
                }

                is GamesRequest.GetPreview -> {
                    method = HttpMethod.Get
                    requestConfigure(request.fullPath())
                }

                is GamesRequest.GetMyGames -> {
                    method = HttpMethod.Get
                    requestConfigure(request.fullPath())
                }

                is GamesRequest.GetInvites -> {
                    method = HttpMethod.Get
                    requestConfigure(request.fullPath())
                }

                is GamesRequest.GetArchive -> {
                    method = HttpMethod.Get
                    requestConfigure(request.fullPath())
                }

                is GamesRequest.GetUpcoming -> {
                    method = HttpMethod.Get
                    requestConfigure(request.fullPath())
                }

                is GamesRequest.JoinGame -> {
                    method = HttpMethod.Post
                    requestConfigure(request.fullPath())
                }

                is GamesRequest.DeclineGameInvite -> {
                    method = HttpMethod.Delete
                    requestConfigure(request.fullPath())
                }

                is GamesRequest.CancelGame -> {
                    method = HttpMethod.Delete
                    requestConfigure(request.fullPath())
                }

                is GamesRequest.GetPlayersToRate -> {
                    method = HttpMethod.Get
                    requestConfigure(request.fullPath())
                }

                is GamesRequest.RatePlayers -> {
                    method = HttpMethod.Post
                    requestConfigure(request.fullPath(), body = request.players)
                }

                is GamesRequest.SkipRating -> {
                    method = HttpMethod.Post
                    requestConfigure(request.fullPath())
                }
            }
        }
    }

    override suspend fun getResponseBodyByRequestType(
        requestType: GamesRequest,
        httpResponse: HttpResponse
    ): GamesResponse {
        return when (requestType) {
            is GamesRequest.CreateGame -> httpResponse.body<GamesResponse.CreateGame>()

            is GamesRequest.GetGameDetails -> httpResponse.body<GamesResponse.GetGameDetails>()

            is GamesRequest.GetPreview -> httpResponse.body<GamesResponse.GetPreview>()

            is GamesRequest.GetMyGames -> httpResponse.body<GamesResponse.GetMyGames>()

            is GamesRequest.GetArchive -> httpResponse.body<GamesResponse.GetArchive>()

            is GamesRequest.GetInvites -> httpResponse.body<GamesResponse.GetInvites>()

            is GamesRequest.GetUpcoming -> httpResponse.body<GamesResponse.GetUpcoming>()

            is GamesRequest.JoinGame -> httpResponse.body<GamesResponse.JoinGame>()

            is GamesRequest.DeclineGameInvite -> GamesResponse.DeclineGameInvite()

            is GamesRequest.GetPlayersToRate -> httpResponse.body<GamesResponse.GetPlayersToRate>()

            is GamesRequest.RatePlayers -> GamesResponse.RatePlayers()

            is GamesRequest.SkipRating -> GamesResponse.SkipRating()

            is GamesRequest.InvitePlayersToGame -> GamesResponse.InvitePlayersToGame()

            is GamesRequest.CancelGame -> GamesResponse.CancelGame()
        }
    }
}
