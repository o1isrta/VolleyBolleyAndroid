package cy.volleybolley.players.domain.repository

import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.players.domain.model.Player
import cy.volleybolley.players.domain.model.PlayerDetail

interface PlayersRepository {
    suspend fun getAllPlayers(): VolleyResult<List<Player>, Throwable>
    suspend fun searchPlayers(query: String): VolleyResult<List<Player>, Throwable>
    suspend fun getPlayerDetail(playerId: Int): VolleyResult<PlayerDetail, Throwable>
    suspend fun addToFavorites(playerId: Int): VolleyResult<Player, Throwable>
    suspend fun removeFromFavorites(playerId: Int): VolleyResult<Unit, Throwable>
}
