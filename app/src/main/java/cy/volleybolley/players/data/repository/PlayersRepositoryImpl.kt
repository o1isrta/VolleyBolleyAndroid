package cy.volleybolley.players.data.repository

import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.players.data.mapper.toDomain
import cy.volleybolley.players.data.network.PlayerRequest
import cy.volleybolley.players.data.network.PlayerResponse
import cy.volleybolley.players.data.network.PlayersNetworkClient
import cy.volleybolley.players.domain.model.Player
import cy.volleybolley.players.domain.model.PlayerDetail
import cy.volleybolley.players.domain.repository.PlayersRepository

class PlayersRepositoryImpl(
    private val networkClient: PlayersNetworkClient
) : PlayersRepository {

    companion object {
        private const val ERROR_INVALID_RESPONSE = "Invalid response"
        private const val ERROR_FETCH_PLAYERS = "Failed to fetch players"
        private const val ERROR_SEARCH = "Search failed"
        private const val ERROR_DETAIL = "Failed to load player detail"
        private const val ERROR_ADD_TO_FAVORITES = "Failed to add to favorites"
        private const val ERROR_REMOVE_FROM_FAVORITES = "Failed to remove from favorites"
    }

    override suspend fun getAllPlayers(): VolleyResult<List<Player>, Throwable> {
        val response = networkClient.getResponse(PlayerRequest.GetAllPlayers)
        val body = response.body as? PlayerResponse.GetAllPlayers
            ?: return VolleyResult.Failure(Throwable(ERROR_INVALID_RESPONSE))

        return if (response.isSuccess) {
            VolleyResult.Success(body.players.map { it.toDomain() })
        } else {
            VolleyResult.Failure(Throwable(ERROR_FETCH_PLAYERS))
        }
    }

    override suspend fun searchPlayers(query: String): VolleyResult<List<Player>, Throwable> {
        val response = networkClient.getResponse(PlayerRequest.SearchPlayers(query))
        val body = response.body as? PlayerResponse.SearchPlayers
            ?: return VolleyResult.Failure(Throwable(ERROR_INVALID_RESPONSE))

        return if (response.isSuccess) {
            VolleyResult.Success(body.players.map { it.toDomain() })
        } else {
            VolleyResult.Failure(Throwable(ERROR_SEARCH))
        }
    }

    override suspend fun getPlayerDetail(playerId: Int): VolleyResult<PlayerDetail, Throwable> {
        val response = networkClient.getResponse(PlayerRequest.GetPlayerDetail(playerId))
        val body = response.body as? PlayerResponse.GetPlayerDetail
            ?: return VolleyResult.Failure(Throwable(ERROR_INVALID_RESPONSE))

        return if (response.isSuccess) {
            VolleyResult.Success(body.player.toDomain())
        } else {
            VolleyResult.Failure(Throwable(ERROR_DETAIL))
        }
    }

    override suspend fun addToFavorites(playerId: Int): VolleyResult<Player, Throwable> {
        val response = networkClient.getResponse(PlayerRequest.AddToFavorites(playerId))
        val body = response.body as? PlayerResponse.AddToFavorites
            ?: return VolleyResult.Failure(Throwable(ERROR_INVALID_RESPONSE))

        return if (response.isSuccess) {
            VolleyResult.Success(body.player.toDomain())
        } else {
            VolleyResult.Failure(Throwable(ERROR_ADD_TO_FAVORITES))
        }
    }

    override suspend fun removeFromFavorites(playerId: Int): VolleyResult<Unit, Throwable> {
        val response = networkClient.getResponse(PlayerRequest.RemoveFromFavorites(playerId))
        return if (response.isSuccess) {
            VolleyResult.Success(Unit)
        } else {
            VolleyResult.Failure(Throwable(ERROR_REMOVE_FROM_FAVORITES))
        }
    }
}
