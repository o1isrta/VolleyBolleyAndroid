package cy.volleybolley.core.data.players.repository

import cy.volleybolley.core.data.network.impl.PlayersNetworkClient
import cy.volleybolley.core.data.network.model.PlayerRequest
import cy.volleybolley.core.data.network.model.PlayerResponse
import cy.volleybolley.core.data.players.mapper.toDomain
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.core.domain.players.model.Player
import cy.volleybolley.core.domain.players.model.PlayerDetail
import cy.volleybolley.core.domain.players.repository.PlayersRepository

class PlayersRepositoryImpl(
    private val networkClient: PlayersNetworkClient
) : PlayersRepository {

    override suspend fun getAllPlayers(): VolleyResult<List<Player>, Throwable> {
        val response = networkClient.getResponse(PlayerRequest.GetAllPlayers)
        val body = response.body

        return if (response.isSuccess && body is PlayerResponse.PlayerList) {
            VolleyResult.Success(body.players.map { it.toDomain() })
        } else {
            VolleyResult.Failure(Throwable("Failed to fetch players"))
        }
    }

    override suspend fun searchPlayers(query: String): VolleyResult<List<Player>, Throwable> {
        val response = networkClient.getResponse(PlayerRequest.SearchPlayers(query))
        val body = response.body

        return if (response.isSuccess && body is PlayerResponse.PlayerList) {
            VolleyResult.Success(body.players.map { it.toDomain() })
        } else {
            VolleyResult.Failure(Throwable("Search failed"))
        }
    }

    override suspend fun getPlayerDetail(playerId: Int): VolleyResult<PlayerDetail, Throwable> {
        val response = networkClient.getResponse(PlayerRequest.GetPlayerDetail(playerId))
        val body = response.body

        return if (response.isSuccess && body is PlayerResponse.PlayerDetail) {
            VolleyResult.Success(body.player.toDomain())
        } else {
            VolleyResult.Failure(Throwable("Failed to load player detail"))
        }
    }

    override suspend fun addToFavorites(playerId: Int): VolleyResult<Player, Throwable> {
        val response = networkClient.getResponse(PlayerRequest.AddToFavorites(playerId))
        val body = response.body

        return if (response.isSuccess && body is PlayerResponse.PlayerList && body.players.isNotEmpty()) {
            VolleyResult.Success(body.players.first().toDomain())
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
