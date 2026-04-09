package cy.volleybolley.players.data.repository

import cy.volleybolley.core.data.network.api.NetworkClient
import cy.volleybolley.core.data.network.model.mapToErrorType
import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.players.data.mapper.toDomain
import cy.volleybolley.players.data.network.PlayerRequest
import cy.volleybolley.players.data.network.PlayerResponse
import cy.volleybolley.players.domain.model.Player
import cy.volleybolley.players.domain.model.PlayerDetail
import cy.volleybolley.players.domain.repository.PlayersRepository

class PlayersRepositoryImpl(
    private val networkClient: NetworkClient<PlayerRequest, PlayerResponse>
) : PlayersRepository {

    override suspend fun getAllPlayers(): VolleyResult<List<Player>, ErrorType> {
        val response = networkClient.getResponse(PlayerRequest.GetAllPlayers())
        val error = if (!response.isSuccess) response.resultCode.mapToErrorType() else null
        val body = response.body as? PlayerResponse.GetAllPlayers

        return when {
            error != null -> VolleyResult.Failure(error)
            body == null -> VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
            else -> VolleyResult.Success(body.players.map { it.toDomain() })
        }
    }

    override suspend fun getPlayerDetail(playerId: Int): VolleyResult<PlayerDetail, ErrorType> {
        val response = networkClient.getResponse(PlayerRequest.GetPlayerDetail(playerId = playerId))
        val error = if (!response.isSuccess) response.resultCode.mapToErrorType() else null
        val body = response.body as? PlayerResponse.GetPlayerDetail

        return when {
            error != null -> VolleyResult.Failure(error)
            body == null -> VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
            else -> VolleyResult.Success(body.player.toDomain())
        }
    }

    override suspend fun addToFavorites(playerId: Int): VolleyResult<Unit, ErrorType> {
        val response = networkClient.getResponse(PlayerRequest.AddToFavorites(playerId = playerId))
        val error = if (!response.isSuccess) response.resultCode.mapToErrorType() else null
        val body = response.body as? PlayerResponse.AddToFavorites

        return when {
            error != null -> VolleyResult.Failure(error)
            body == null -> VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
            else -> VolleyResult.Success(Unit)
        }
    }

    override suspend fun removeFromFavorites(playerId: Int): VolleyResult<Unit, ErrorType> {
        val response = networkClient.getResponse(PlayerRequest.RemoveFromFavorites(playerId = playerId))
        val error = if (!response.isSuccess) response.resultCode.mapToErrorType() else null

        return if (error != null) {
            VolleyResult.Failure(error)
        } else {
            VolleyResult.Success(Unit)
        }
    }
}
