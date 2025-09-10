package cy.volleybolley.games.data.network

import cy.volleybolley.BuildConfig
import cy.volleybolley.core.data.network.impl.KtorNetworkClient
import io.ktor.client.call.body
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpMethod

@Suppress("LongMethod")
class GamesNetworkClient : KtorNetworkClient<GamesRequest, GamesResponse>() {
    override suspend fun sendRequestByType(request: GamesRequest): HttpResponse {
        return httpClient.request(urlString = BuildConfig.BASE_URL) {
            when (request) {
                is GamesRequest.CreateGame -> {
                    method = HttpMethod.Post
                    requestConfigure(path = request.path, accessToken = request.accessToken)
                }

                is GamesRequest.GetGameDetails -> {
                    method = HttpMethod.Get
                    requestConfigure(path = request.fullPath(), accessToken = request.accessToken)
                }

                is GamesRequest.CancelGame -> {
                    method = HttpMethod.Post
                    requestConfigure(path = request.fullPath(), accessToken = request.accessToken)
                }

                is GamesRequest.InvitePlayersToGame -> {
                    method = HttpMethod.Post
                    requestConfigure(path = request.fullPath(), accessToken = request.accessToken)
                }

                is GamesRequest.GetPreview -> {
                    method = HttpMethod.Get
                    requestConfigure(path = request.fullPath(), accessToken = request.accessToken)
                }

                is GamesRequest.GetArchive -> {
                    method = HttpMethod.Get
                    requestConfigure(path = request.fullPath(), accessToken = request.accessToken)
                }

                is GamesRequest.GetInvites -> {
                    method = HttpMethod.Get
                    requestConfigure(path = request.fullPath(), accessToken = request.accessToken)
                }

                is GamesRequest.GetMyGames -> {
                    method = HttpMethod.Get
                    requestConfigure(path = request.fullPath(), accessToken = request.accessToken)
                }

                is GamesRequest.GetUpcoming -> {
                    method = HttpMethod.Get
                    requestConfigure(path = request.fullPath(), accessToken = request.accessToken)
                }

                is GamesRequest.JoinGame -> {
                    method = HttpMethod.Post
                    requestConfigure(path = request.fullPath(), accessToken = request.accessToken)
                }

                is GamesRequest.DeclineGameInvite -> {
                    method = HttpMethod.Delete
                    requestConfigure(path = request.fullPath(), accessToken = request.accessToken)
                }

                is GamesRequest.GetPlayersToRate -> {
                    method = HttpMethod.Get
                    requestConfigure(path = request.fullPath(), accessToken = request.accessToken)
                }

                is GamesRequest.RatePlayers -> {
                    method = HttpMethod.Post
                    requestConfigure(path = request.fullPath(), accessToken = request.accessToken)
                }

                is GamesRequest.SkipRating -> {
                    method = HttpMethod.Post
                    requestConfigure(path = request.fullPath(), accessToken = request.accessToken)
                }
            }
        }
    }

    override suspend fun getResponseBodyByRequestType(
        requestType: GamesRequest, httpResponse: HttpResponse
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

            is GamesRequest.DeclineGameInvite -> httpResponse.body<GamesResponse.DeclineGameInvite>()

            is GamesRequest.GetPlayersToRate -> httpResponse.body<GamesResponse.GetPlayersToRate>()

            is GamesRequest.RatePlayers -> httpResponse.body<GamesResponse.RatePlayers>()

            is GamesRequest.SkipRating -> httpResponse.body<GamesResponse.SkipRating>()

            is GamesRequest.InvitePlayersToGame -> httpResponse.body<GamesResponse.InvitePlayersToGame>()

            is GamesRequest.CancelGame -> httpResponse.body<GamesResponse.CancelGame>()
        }
    }
}
