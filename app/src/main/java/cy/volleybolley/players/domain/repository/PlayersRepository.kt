package cy.volleybolley.players.domain.repository

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.players.domain.model.Player
import cy.volleybolley.players.domain.model.PlayerDetail

interface PlayersRepository {
    suspend fun getAllPlayers(): VolleyResult<List<Player>, ErrorType>
    suspend fun getFavoritePlayers(): VolleyResult<List<Player>, ErrorType>
    suspend fun getPlayerDetail(playerId: Int): VolleyResult<PlayerDetail, ErrorType>
    suspend fun addToFavorites(playerId: Int): VolleyResult<Player, ErrorType>
    suspend fun removeFromFavorites(playerId: Int): VolleyResult<Unit, ErrorType>

    fun getCachedAllPlayers(): List<Player>
    fun getCachedFavoritePlayers(): List<Player>
}
