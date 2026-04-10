package cy.volleybolley.players.domain.usecase

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.players.domain.model.Player

interface GetPlayersInteractor {

    suspend fun updatePlayers(showAllPlayers: Boolean): VolleyResult<List<Player>, ErrorType>

    fun fetchCachedPlayers(): List<Player>

    fun fetchCachedFavoritePlayers(): List<Player>

}
