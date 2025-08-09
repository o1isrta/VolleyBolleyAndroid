package cy.volleybolley.games.data

import cy.volleybolley.core.data.network.api.NetworkClient
import cy.volleybolley.core.data.network.model.mapToErrorType
import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.data.dto.mappers.toData
import cy.volleybolley.games.data.dto.mappers.toDomain
import cy.volleybolley.games.data.network.GamesRequest
import cy.volleybolley.games.data.network.GamesResponse
import cy.volleybolley.games.domain.api.GamesRepository
import cy.volleybolley.games.domain.model.Game
import cy.volleybolley.games.domain.model.GameDetails
import cy.volleybolley.games.domain.model.Tournament

class GamesRepositoryImpl(
    val networkClient: NetworkClient<GamesRequest, GamesResponse>
) : GamesRepository {
    override suspend fun createGame(game: Game): VolleyResult<Game, ErrorType> {
        val response = networkClient.getResponse(GamesRequest.CreateGame(game = game.toData()))

        if (!response.isSuccess) {
            return VolleyResult.Failure(response.resultCode.mapToErrorType())
        }

        val game = (response.body as? GamesResponse.CreateGame)?.toDomain()
        return game?.let { VolleyResult.Success(it) } ?: VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
    }

    override suspend fun getGameDetails(gameId: Int): VolleyResult<GameDetails, ErrorType> {
        val response = networkClient.getResponse(GamesRequest.GetGameDetails(gameId = gameId))

        if (!response.isSuccess) {
            return VolleyResult.Failure(response.resultCode.mapToErrorType())
        }

        val game = (response.body as? GamesResponse.GetGameDetails)?.toDomain()
        return game?.let { VolleyResult.Success(it) } ?: VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
    }

    suspend fun createTournament(tournament: Tournament): VolleyResult<Tournament, ErrorType> {
        val response = networkClient.getResponse(GamesRequest.CreateTournament(tournament = tournament))
    }

        /*
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

            is GamesRequest.JoinTournament -> httpClient.post(BuildConfig.BASE_URL) {
                url {
                    path(request.fullPath())
                }
            }

            is GamesRequest.DeclineGameInvite -> httpClient.delete(BuildConfig.BASE_URL) {
                url {
                    path(request.fullPath())
                }
            }

            is GamesRequest.DeclineTournamentInvite -> httpClient.delete(BuildConfig.BASE_URL) {
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

         */
}
