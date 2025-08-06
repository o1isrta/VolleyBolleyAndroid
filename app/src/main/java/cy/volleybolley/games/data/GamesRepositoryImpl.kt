package cy.volleybolley.games.data

import cy.volleybolley.core.data.network.api.NetworkClient
import cy.volleybolley.core.data.network.model.mapToErrorType
import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.data.dto.toData
import cy.volleybolley.games.data.dto.toDomain
import cy.volleybolley.games.data.network.GamesRequest
import cy.volleybolley.games.data.network.GamesResponse
import cy.volleybolley.games.domain.api.GamesRepository
import cy.volleybolley.games.domain.model.Game
import cy.volleybolley.games.domain.model.GameDetails

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
}
