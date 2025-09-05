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
import cy.volleybolley.games.domain.model.event.game.CreateGame
import cy.volleybolley.games.domain.model.event.game.CreatedGame
import cy.volleybolley.games.domain.model.event.game.GameDetails

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

    override suspend fun cancelGame(gameId: Int): VolleyResult<Unit, ErrorType> {
        val response = networkClient.getResponse(GamesRequest.CancelGame(gameId = gameId))

        return if (response.isSuccess) {
            VolleyResult.Success(Unit)
        } else {
            VolleyResult.Failure(response.resultCode.mapToErrorType())
        }
    }
}
