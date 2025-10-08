package cy.volleybolley.games.data

import cy.volleybolley.core.data.network.api.NetworkClient
import cy.volleybolley.core.data.network.model.mapToErrorType
import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.data.dto.mappers.toData
import cy.volleybolley.games.data.dto.mappers.toDomain
import cy.volleybolley.games.data.network.GamesRequest
import cy.volleybolley.games.data.network.GamesResponse
import cy.volleybolley.games.domain.api.GameRatingRepository
import cy.volleybolley.games.domain.model.entity.PlayerShort
import cy.volleybolley.games.domain.model.entity.RatePlayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class GameRatingRepositoryImpl(
    private val networkClient: NetworkClient<GamesRequest, GamesResponse>,
    private val applicationScope: CoroutineScope
) : GameRatingRepository {
    override suspend fun getPlayersToRate(gameId: Int): VolleyResult<List<PlayerShort>, ErrorType> {
        val response = networkClient.getResponse(GamesRequest.GetPlayersToRate(gameId = gameId))

        if (!response.isSuccess) return VolleyResult.Failure(response.resultCode.mapToErrorType())

        val preview = (response.body as? GamesResponse.GetPlayersToRate)?.players?.toDomain()
        return preview?.let { VolleyResult.Success(it) } ?: VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
    }

    override fun ratePlayers(
        gameId: Int,
        players: List<RatePlayer>,
        onResult: (VolleyResult<Unit, ErrorType>) -> Unit
    ) {
        applicationScope.launch {
            val response =
                networkClient.getResponse(GamesRequest.RatePlayers(gameId = gameId, players = players.toData()))

            val result: VolleyResult<Unit, ErrorType> = if (response.isSuccess) {
                VolleyResult.Success(Unit)
            } else {
                VolleyResult.Failure(response.resultCode.mapToErrorType())
            }

            onResult(result)
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
