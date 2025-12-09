package cy.volleybolley.games.data.network

import cy.volleybolley.core.data.network.impl.KtorNetworkClient
import io.ktor.client.call.body
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpMethod

@Suppress("LongMethod", "ComplexMethod")
class TournamentsNetworkClient : KtorNetworkClient<TournamentsRequest, TournamentsResponse>() {
    override suspend fun sendRequestByType(request: TournamentsRequest): HttpResponse {
        return httpClient.request {
            when (request) {
                is TournamentsRequest.CreateTournament -> {
                    method = HttpMethod.Post
                    requestConfigure(request.path, body = request.tournament)
                }

                is TournamentsRequest.GetTournamentDetails -> {
                    method = HttpMethod.Get
                    requestConfigure(request.fullPath())
                }

                is TournamentsRequest.CancelTournament -> {
                    method = HttpMethod.Delete
                    requestConfigure(request.fullPath())
                }

                is TournamentsRequest.InvitePlayersToTournament -> {
                    method = HttpMethod.Post
                    requestConfigure(request.fullPath(), body = request.players)
                }

                is TournamentsRequest.JoinTournament -> {
                    method = HttpMethod.Post
                    requestConfigure(request.fullPath(), body = request.teamId)
                }

                is TournamentsRequest.DeclineTournamentInvite -> {
                    method = HttpMethod.Delete
                    requestConfigure(request.fullPath())
                }

                is TournamentsRequest.GetPlayersToRate -> {
                    method = HttpMethod.Get
                    requestConfigure(request.fullPath())
                }

                is TournamentsRequest.RatePlayers -> {
                    method = HttpMethod.Post
                    requestConfigure(request.fullPath(), body = request.players)
                }

                is TournamentsRequest.SkipRating -> {
                    method = HttpMethod.Post
                    requestConfigure(request.fullPath())
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

            is TournamentsRequest.DeclineTournamentInvite -> TournamentsResponse.DeclineTournamentInvite()

            is TournamentsRequest.GetPlayersToRate -> httpResponse.body<TournamentsResponse.GetPlayersToRate>()

            is TournamentsRequest.GetTournamentDetails -> httpResponse.body<TournamentsResponse.GetTournamentDetails>()

            is TournamentsRequest.InvitePlayersToTournament -> TournamentsResponse.InvitePlayersToTournament()

            is TournamentsRequest.JoinTournament -> httpResponse.body<TournamentsResponse.JoinTournament>()

            is TournamentsRequest.RatePlayers -> TournamentsResponse.RatePlayers()

            is TournamentsRequest.SkipRating -> TournamentsResponse.SkipRating()

            is TournamentsRequest.CancelTournament -> TournamentsResponse.CancelTournament()
        }
    }
}
