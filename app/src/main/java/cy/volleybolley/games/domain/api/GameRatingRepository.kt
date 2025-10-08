package cy.volleybolley.games.domain.api

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.domain.model.entity.PlayerShort
import cy.volleybolley.games.domain.model.entity.RatePlayer

interface GameRatingRepository {
    suspend fun getPlayersToRate(gameId: Int): VolleyResult<List<PlayerShort>, ErrorType>
    suspend fun ratePlayers(gameId: Int, players: List<RatePlayer>): VolleyResult<Unit, ErrorType>
    suspend fun skipRating(gameId: Int): VolleyResult<Unit, ErrorType>
}

