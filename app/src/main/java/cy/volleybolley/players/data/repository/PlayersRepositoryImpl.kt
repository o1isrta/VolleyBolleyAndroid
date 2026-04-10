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

    private var cachedAllPlayers: List<Player>? = null
    private var cachedFavoritePlayers: List<Player>? = null
    private var cachedTimestamp: Long = 0

    override fun getCachedAllPlayers(): List<Player> = cachedAllPlayers ?: emptyList()
    override fun getCachedFavoritePlayers(): List<Player> = cachedFavoritePlayers ?: emptyList()

    override suspend fun getAllPlayers(): VolleyResult<List<Player>, ErrorType> {
        val elapsedTime = System.currentTimeMillis() - cachedTimestamp
        return if (cachedAllPlayers != null && elapsedTime < GET_PLAYERS_DELAY) {
            VolleyResult.Success(cachedAllPlayers!!)
        } else {
            getPlayersFromServer()
        }
    }

    override suspend fun getFavoritePlayers(): VolleyResult<List<Player>, ErrorType> {
        val elapsedTime = System.currentTimeMillis() - cachedTimestamp
        return if (cachedFavoritePlayers != null && elapsedTime < GET_PLAYERS_DELAY) {
            VolleyResult.Success(cachedFavoritePlayers!!)
        } else {
            getPlayersFromServer(justFavoriteOnReturn = true)
        }
    }

    private suspend fun getPlayersFromServer(
        justFavoriteOnReturn: Boolean = false
    ): VolleyResult<List<Player>, ErrorType> {
        val response = networkClient.getResponse(PlayerRequest.GetAllPlayers())
        val error = if (!response.isSuccess) response.resultCode.mapToErrorType() else null
        val body = response.body as? PlayerResponse.GetAllPlayers

        return when {
            error != null -> VolleyResult.Failure(error)
            body == null -> VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
            else -> {
                cachedAllPlayers = body.players.map { it.toDomain() }
                cachedFavoritePlayers = cachedAllPlayers!!.filter { it.isFavorite }
                VolleyResult.Success(
                    data = if (justFavoriteOnReturn) cachedFavoritePlayers!! else cachedAllPlayers!!
                )
            }
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

    override suspend fun addToFavorites(playerId: Int): VolleyResult<Player, ErrorType> {
        val response = networkClient.getResponse(PlayerRequest.AddToFavorites(playerId = playerId))
        val error = if (!response.isSuccess) response.resultCode.mapToErrorType() else null
        val body = response.body as? PlayerResponse.AddToFavorites

        return when {
            error != null -> VolleyResult.Failure(error)
            body == null -> VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
            else -> VolleyResult.Success(body.favoritePlayer.toDomain())
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

    companion object {
        const val GET_PLAYERS_DELAY: Long = 300_000L
    }
}
