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
import cy.volleybolley.games.domain.model.entity.PlayerShort
import cy.volleybolley.games.domain.model.entity.RatePlayer
import cy.volleybolley.games.domain.model.event.Event
import cy.volleybolley.games.domain.model.event.Preview
import cy.volleybolley.games.domain.model.event.game.CreateGame
import cy.volleybolley.games.domain.model.event.game.CreatedGame
import cy.volleybolley.games.domain.model.event.game.GameDetails
import cy.volleybolley.games.domain.model.event.game.JoinedGame

class GamesRepositoryImpl(
    val networkClient: NetworkClient<GamesRequest, GamesResponse>
) : GamesRepository {
    override suspend fun createGame(game: CreateGame): VolleyResult<CreatedGame, ErrorType> {
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

    override suspend fun invitePlayersToGame(gameId: Int, players: List<PlayerShort>): VolleyResult<Unit, ErrorType> {
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

    override suspend fun getPreview(): VolleyResult<Preview, ErrorType> {
        val response = networkClient.getResponse(GamesRequest.GetPreview())

        if (!response.isSuccess) {
            return VolleyResult.Failure(response.resultCode.mapToErrorType())
        }

        val preview = (response.body as? GamesResponse.GetPreview)?.toDomain()
        return preview?.let { VolleyResult.Success(it) } ?: VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
    }

    override suspend fun getMyGames(): VolleyResult<List<Event>, ErrorType> {
        val response = networkClient.getResponse(GamesRequest.GetMyGames())

        if (!response.isSuccess) return VolleyResult.Failure(response.resultCode.mapToErrorType())

        val myGames = (response.body as? GamesResponse.GetMyGames)

        return myGames?.let { VolleyResult.Success(data = it.toDomain()) }
            ?: return VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
    }

    override suspend fun getInvites(): VolleyResult<List<Event>, ErrorType> {
        val response = networkClient.getResponse(GamesRequest.GetInvites())

        if (!response.isSuccess) return VolleyResult.Failure(response.resultCode.mapToErrorType())

        val invites = (response.body as? GamesResponse.GetInvites)

        return invites?.let { VolleyResult.Success(data = it.toDomain()) }
            ?: return VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
    }

    override suspend fun getArchive(): VolleyResult<List<Event>, ErrorType> {
        val response = networkClient.getResponse(GamesRequest.GetArchive())

        if (!response.isSuccess) return VolleyResult.Failure(response.resultCode.mapToErrorType())

        val archives = (response.body as? GamesResponse.GetArchive)

        return archives?.let { VolleyResult.Success(data = it.toDomain()) }
            ?: return VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
    }

    override suspend fun getUpcoming(): VolleyResult<List<Event>, ErrorType> {
        val response = networkClient.getResponse(GamesRequest.GetUpcoming())

        if (!response.isSuccess) return VolleyResult.Failure(response.resultCode.mapToErrorType())

        val upcoming = (response.body as? GamesResponse.GetUpcoming)

        return upcoming?.let { VolleyResult.Success(data = it.toDomain()) }
            ?: return VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
    }

    override suspend fun joinGame(gameId: Int): VolleyResult<JoinedGame, ErrorType> {
        val response = networkClient.getResponse(GamesRequest.JoinGame(gameId = gameId))

        if (!response.isSuccess) return VolleyResult.Failure(response.resultCode.mapToErrorType())

        val game = (response.body as? GamesResponse.JoinGame)

        return game?.let {
            VolleyResult.Success(data = game.toDomain())
        } ?: VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
    }

    override suspend fun declineGameInvite(gameId: Int): VolleyResult<Unit, ErrorType> {
        val response = networkClient.getResponse(GamesRequest.DeclineGameInvite(gameId = gameId))

        return if (response.isSuccess) {
            VolleyResult.Success(Unit)
        } else {
            VolleyResult.Failure(response.resultCode.mapToErrorType())
        }
    }

    override suspend fun cancelGame(gameId: Int): VolleyResult<Unit, ErrorType> {
        val response = networkClient.getResponse(GamesRequest.CancelGame(gameId = gameId))

        return if (response.isSuccess) {
            VolleyResult.Success(Unit)
        } else {
            VolleyResult.Failure(response.resultCode.mapToErrorType())
        }
    }

    override suspend fun getPlayersToRate(gameId: Int): VolleyResult<List<PlayerShort>, ErrorType> {
        val response = networkClient.getResponse(GamesRequest.GetPlayersToRate(gameId = gameId))

        if (!response.isSuccess) return VolleyResult.Failure(response.resultCode.mapToErrorType())

        val preview = (response.body as? GamesResponse.GetPlayersToRate)?.players?.toDomain()
        return preview?.let { VolleyResult.Success(it) } ?: VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
    }

    override suspend fun ratePlayers(gameId: Int, players: List<RatePlayer>): VolleyResult<Unit, ErrorType> {
        val response = networkClient.getResponse(GamesRequest.RatePlayers(gameId = gameId, players = players.toData()))

        return if (response.isSuccess) {
            VolleyResult.Success(Unit)
        } else {
            VolleyResult.Failure(response.resultCode.mapToErrorType())
        }
    }

    override suspend fun skipRating(gameId: Int): VolleyResult<Unit, ErrorType> {
        val response = networkClient.getResponse(GamesRequest.SkipRating(gameId = gameId))

        return if (response.isSuccess) {
            VolleyResult.Success(Unit)
        } else {
            VolleyResult.Failure(response.resultCode.mapToErrorType())
        }
    }
}
