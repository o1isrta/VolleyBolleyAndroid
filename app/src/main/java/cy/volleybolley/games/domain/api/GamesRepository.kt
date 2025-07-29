package cy.volleybolley.games.domain.api

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.domain.model.Game

interface GamesRepository {
    suspend fun createGame(game: Game): VolleyResult<Game?, ErrorType>
}
