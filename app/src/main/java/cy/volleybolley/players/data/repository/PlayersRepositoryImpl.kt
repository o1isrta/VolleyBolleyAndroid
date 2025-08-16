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

    override suspend fun getAllPlayers(): VolleyResult<List<Player>, Throwable> {
        val response = networkClient.getResponse(PlayerRequest.GetAllPlayers)
        val body = response.body as? PlayerResponse.GetAllPlayers
            ?: return VolleyResult.Failure(Throwable("Invalid response"))

        return if (response.isSuccess) {
            VolleyResult.Success(body.players.map { it.toDomain() })
        } else {
            VolleyResult.Failure(Throwable("Failed to fetch players"))
        }
    }

    override suspend fun searchPlayers(query: String): VolleyResult<List<Player>, Throwable> {
        val response = networkClient.getResponse(PlayerRequest.SearchPlayers(query))
        val body = response.body as? PlayerResponse.SearchPlayers
            ?: return VolleyResult.Failure(Throwable("Invalid response"))

        return if (response.isSuccess) {
            VolleyResult.Success(body.players.map { it.toDomain() })
        } else {
            VolleyResult.Failure(Throwable("Search failed"))
        }
    }

    override suspend fun getPlayerDetail(playerId: Int): VolleyResult<PlayerDetail, Throwable> {
        val response = networkClient.getResponse(PlayerRequest.GetPlayerDetail(playerId))
        val body = response.body as? PlayerResponse.GetPlayerDetail
            ?: return VolleyResult.Failure(Throwable("Invalid response"))

        return if (response.isSuccess) {
            VolleyResult.Success(body.player.toDomain())
        } else {
            VolleyResult.Failure(Throwable("Failed to load player detail"))
        }
    }

    override suspend fun addToFavorites(playerId: Int): VolleyResult<Player, Throwable> {
        val response = networkClient.getResponse(PlayerRequest.AddToFavorites(playerId))
        val body = response.body as? PlayerResponse.AddToFavorites
            ?: return VolleyResult.Failure(Throwable("Invalid response"))

        return if (response.isSuccess) {
            VolleyResult.Success(body.player.toDomain())
        } else {
            VolleyResult.Failure(Throwable("Failed to add to favorites"))
        }
    }

    override suspend fun removeFromFavorites(playerId: Int): VolleyResult<Unit, Throwable> {
        val response = networkClient.getResponse(PlayerRequest.RemoveFromFavorites(playerId))
        return if (response.isSuccess) {
            VolleyResult.Success(Unit)
        } else {
            VolleyResult.Failure(Throwable("Failed to remove from favorites"))
        }
    }
}
