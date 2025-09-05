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

@Suppress("LongMethod")
class TournamentsNetworkClient : KtorNetworkClient<TournamentsRequest, TournamentsResponse>() {
    override suspend fun sendRequestByType(request: TournamentsRequest): HttpResponse {
        return when (request) {
            is TournamentsRequest.CreateTournament -> httpClient.post(BuildConfig.BASE_URL) {
                url {
                    path(request.path)
                }
                contentType(ContentType.Application.Json)
                setBody(request.tournament)
            }

            is TournamentsRequest.GetTournamentDetails -> httpClient.get(BuildConfig.BASE_URL) {
                url {
                    path(request.fullPath())
                }
            }

            is TournamentsRequest.CancelTournament -> httpClient.post(BuildConfig.BASE_URL) {
                url {
                    path(request.fullPath())
                }
            }

            is TournamentsRequest.InvitePlayersToTournament -> httpClient.post(BuildConfig.BASE_URL) {
                url {
                    path(request.fullPath())
                }
                contentType(ContentType.Application.Json)
                setBody(request.players)
            }

            is TournamentsRequest.JoinTournament -> httpClient.post(BuildConfig.BASE_URL) {
                url {
                    path(request.fullPath())
                }
            }

            is TournamentsRequest.DeclineTournamentInvite -> httpClient.get(BuildConfig.BASE_URL) {
                url {
                    path(request.fullPath())
                }
            }

            is TournamentsRequest.GetPlayersToRate -> httpClient.get(BuildConfig.BASE_URL) {
                url {
                    path(request.fullPath())
                }
            }

            is TournamentsRequest.RatePlayers -> httpClient.post(BuildConfig.BASE_URL) {
                url {
                    path(request.fullPath())
                }
                contentType(ContentType.Application.Json)
                setBody(request.players)
            }

            is TournamentsRequest.SkipRating -> httpClient.post(BuildConfig.BASE_URL) {
                url {
                    path(request.fullPath())
                }
            }
        }
    }

    override suspend fun getResponseBodyByRequestType(
        requestType: TournamentsRequest,
        httpResponse: HttpResponse
    ): TournamentsResponse {
        return when (requestType) {
            is TournamentsRequest.CreateTournament -> {
                httpResponse.body<TournamentsResponse.CreateTournament>()
            }

            is TournamentsRequest.DeclineTournamentInvite -> {
                httpResponse.body<TournamentsResponse.DeclineTournamentInvite>()
            }

            is TournamentsRequest.GetPlayersToRate -> {
                httpResponse.body<TournamentsResponse.GetPlayersToRate>()
            }

            is TournamentsRequest.GetTournamentDetails -> {
                httpResponse.body<TournamentsResponse.GetTournamentDetails>()
            }

            is TournamentsRequest.InvitePlayersToTournament -> {
                httpResponse.body<TournamentsResponse.InvitePlayersToTournament>()
            }

            is TournamentsRequest.JoinTournament -> {
                httpResponse.body<TournamentsResponse.JoinTournament>()
            }

            is TournamentsRequest.RatePlayers -> {
                httpResponse.body<TournamentsResponse.RatePlayers>()
            }

            is TournamentsRequest.SkipRating -> {
                httpResponse.body<TournamentsResponse.SkipRating>()
            }

            is TournamentsRequest.CancelTournament -> {
                httpResponse.body<TournamentsResponse.CancelTournament>()
            }
        }
    }
}
