package cy.volleybolley.games.data.network

import cy.volleybolley.BuildConfig
import cy.volleybolley.core.data.network.impl.KtorNetworkClient
import io.ktor.client.call.body
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpMethod

@Suppress("LongMethod", "ComplexMethod")
class TournamentsNetworkClient : KtorNetworkClient<TournamentsRequest, TournamentsResponse>() {
    override suspend fun sendRequestByType(request: TournamentsRequest): HttpResponse {
        return httpClient.request(urlString = BuildConfig.BASE_URL) {
            when (request) {
                is TournamentsRequest.CreateTournament -> {
                    method = HttpMethod.Post
                    requestConfigure(
                        request.path,
                        request.accessToken,
                        body = request.tournament
                    )
                }

                is TournamentsRequest.GetTournamentDetails -> {
                    method = HttpMethod.Get
                    requestConfigure(request.fullPath(), request.accessToken)
                }

                is TournamentsRequest.CancelTournament -> {
                    method = HttpMethod.Post
                    requestConfigure(request.fullPath(), request.accessToken)
                }

                is TournamentsRequest.InvitePlayersToTournament -> {
                    method = HttpMethod.Post
                    requestConfigure(
                        request.fullPath(),
                        request.accessToken,
                        body = request.players
                    )
                }

                is TournamentsRequest.JoinTournament -> {
                    method = HttpMethod.Post
                    requestConfigure(request.fullPath(), request.accessToken)
                }

                is TournamentsRequest.DeclineTournamentInvite -> {
                    method = HttpMethod.Delete
                    requestConfigure(request.fullPath(), request.accessToken)
                }

                is TournamentsRequest.GetPlayersToRate -> {
                    method = HttpMethod.Get
                    requestConfigure(request.fullPath(), request.accessToken)
                }

                is TournamentsRequest.RatePlayers -> {
                    method = HttpMethod.Post
                    requestConfigure(
                        request.fullPath(),
                        request.accessToken,
                        body = request.players
                    )
                }

                is TournamentsRequest.SkipRating -> {
                    method = HttpMethod.Post
                    requestConfigure(request.fullPath(), request.accessToken)
                }
            }
        }
    }

    override suspend fun getResponseBodyByRequestType(
        requestType: TournamentsRequest,
        httpResponse: HttpResponse
    ): TournamentsResponse {
        return when (requestType) {
            is TournamentsRequest.CreateTournament -> httpResponse.body<TournamentsResponse.CreateTournament>()

            is TournamentsRequest.DeclineTournamentInvite ->
                httpResponse.body<TournamentsResponse.DeclineTournamentInvite>()

            is TournamentsRequest.GetPlayersToRate -> httpResponse.body<TournamentsResponse.GetPlayersToRate>()

            is TournamentsRequest.GetTournamentDetails -> httpResponse.body<TournamentsResponse.GetTournamentDetails>()

            is TournamentsRequest.InvitePlayersToTournament ->
                httpResponse.body<TournamentsResponse.InvitePlayersToTournament>()

            is TournamentsRequest.JoinTournament -> httpResponse.body<TournamentsResponse.JoinTournament>()

            is TournamentsRequest.RatePlayers -> httpResponse.body<TournamentsResponse.RatePlayers>()

            is TournamentsRequest.SkipRating -> httpResponse.body<TournamentsResponse.SkipRating>()

            is TournamentsRequest.CancelTournament -> httpResponse.body<TournamentsResponse.CancelTournament>()
        }
    }
}
