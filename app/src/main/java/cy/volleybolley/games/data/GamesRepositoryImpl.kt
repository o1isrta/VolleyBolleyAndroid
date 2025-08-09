package cy.volleybolley.games.data

import cy.volleybolley.core.data.network.api.NetworkClient
import cy.volleybolley.core.data.network.model.mapToErrorType
import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.data.dto.mappers.toData
import cy.volleybolley.games.data.dto.mappers.toDomain
import cy.volleybolley.games.data.dto.mappers.toPlayersData
import cy.volleybolley.games.data.network.GamesRequest
import cy.volleybolley.games.data.network.GamesResponse
import cy.volleybolley.games.domain.api.GamesRepository
import cy.volleybolley.games.domain.model.Game
import cy.volleybolley.games.domain.model.GamePreview
import cy.volleybolley.games.domain.model.PlayerShort
import cy.volleybolley.games.domain.model.Preview
import cy.volleybolley.games.domain.model.Tournament
import cy.volleybolley.games.domain.model.TournamentPreview

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

    override suspend fun getGameDetails(gameId: Int): VolleyResult<Game, ErrorType> {
        val response = networkClient.getResponse(GamesRequest.GetGameDetails(gameId = gameId))

        if (!response.isSuccess) {
            return VolleyResult.Failure(response.resultCode.mapToErrorType())
        }

        val game = (response.body as? GamesResponse.GetGameDetails)?.toDomain()
        return game?.let { VolleyResult.Success(it) } ?: VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
    }

    suspend fun createTournament(tournament: Tournament): VolleyResult<Tournament, ErrorType> {
        val response = networkClient.getResponse(GamesRequest.CreateTournament(tournament = tournament.toData()))

        if (!response.isSuccess) {
            return VolleyResult.Failure(response.resultCode.mapToErrorType())
        }

        val tournament = (response.body as? GamesResponse.CreateTournament)?.toDomain()
        return tournament?.let { VolleyResult.Success(it) } ?: VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
    }

    suspend fun getTournamentDetails(tournamentId: Int): VolleyResult<Tournament, ErrorType> {
        val response = networkClient.getResponse(GamesRequest.GetTournamentDetails(tournamentId = tournamentId))

        if (!response.isSuccess) {
            return VolleyResult.Failure(response.resultCode.mapToErrorType())
        }

        val tournament = (response.body as? GamesResponse.GetTournamentDetails)?.toDomain()
        return tournament?.let { VolleyResult.Success(it) } ?: VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
    }

    suspend fun invitePlayersToGame(gameId: Int, players: List<PlayerShort>): VolleyResult<Unit, ErrorType> {
        val response = networkClient.getResponse(
            GamesRequest.InvitePlayersToGame(
                gameId = gameId,
                players = players.toPlayersData()
            )
        )

        return if (response.isSuccess) {
            VolleyResult.Success(Unit)
        } else {
            VolleyResult.Failure(response.resultCode.mapToErrorType())
        }
    }

    suspend fun invitePlayersToTournament(
        tournamentId: Int,
        players: List<PlayerShort>
    ): VolleyResult<Unit, ErrorType> {
        val response = networkClient.getResponse(
            GamesRequest.InvitePlayersToTournament(
                tournamentId = tournamentId,
                players = players.toPlayersData()
            )
        )

        return if (response.isSuccess) {
            VolleyResult.Success(Unit)
        } else {
            VolleyResult.Failure(response.resultCode.mapToErrorType())
        }
    }

    suspend fun getPreview(): VolleyResult<Preview, ErrorType> {
        val response = networkClient.getResponse(GamesRequest.GetPreview())

        if (!response.isSuccess) {
            return VolleyResult.Failure(response.resultCode.mapToErrorType())
        }

        val preview = (response.body as? GamesResponse.GetPreview)?.toDomain()
        return preview?.let { VolleyResult.Success(it) } ?: VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
    }

    suspend fun getMyGames(): VolleyResult<Pair<List<GamePreview>, List<TournamentPreview>>, ErrorType> {
        val response = networkClient.getResponse(GamesRequest.GetMyGames())

        if (!response.isSuccess) return VolleyResult.Failure(response.resultCode.mapToErrorType())

        val myGames = (response.body as? GamesResponse.GetMyGames)
            ?: return VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)

        return VolleyResult.Success(
            myGames.games.map { it.toDomain() } to myGames.tournaments.map { it.toDomain() }
        )
    }

    suspend fun getInvites(): VolleyResult<Pair<List<GamePreview>, List<TournamentPreview>>, ErrorType> {
        val response = networkClient.getResponse(GamesRequest.GetInvites())

        if (!response.isSuccess) return VolleyResult.Failure(response.resultCode.mapToErrorType())

        val myGames = (response.body as? GamesResponse.GetInvites)
            ?: return VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)

        return VolleyResult.Success(
            myGames.games.map { it.toDomain() } to myGames.tournaments.map { it.toDomain() }
        )
    }

    suspend fun getArchive(): VolleyResult<Pair<List<GamePreview>, List<TournamentPreview>>, ErrorType> {
        val response = networkClient.getResponse(GamesRequest.GetArchive())

        if (!response.isSuccess) return VolleyResult.Failure(response.resultCode.mapToErrorType())

        val myGames = (response.body as? GamesResponse.GetArchive)
            ?: return VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)

        return VolleyResult.Success(
            myGames.games.map { it.toDomain() } to myGames.tournaments.map { it.toDomain() }
        )
    }

    suspend fun getUpcoming(): VolleyResult<Pair<List<GamePreview>, List<TournamentPreview>>, ErrorType> {
        val response = networkClient.getResponse(GamesRequest.GetUpcoming())

        if (!response.isSuccess) return VolleyResult.Failure(response.resultCode.mapToErrorType())

        val myGames = (response.body as? GamesResponse.GetUpcoming)
            ?: return VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)

        return VolleyResult.Success(
            myGames.games.map { it.toDomain() } to myGames.tournaments.map { it.toDomain() }
        )
    }
    /*

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
