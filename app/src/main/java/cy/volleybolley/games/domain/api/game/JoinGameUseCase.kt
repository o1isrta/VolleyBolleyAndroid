package cy.volleybolley.games.domain.api.game

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult

interface JoinGameUseCase {
    suspend fun joinGame(gameId: Int): VolleyResult<Unit, ErrorType>
}
