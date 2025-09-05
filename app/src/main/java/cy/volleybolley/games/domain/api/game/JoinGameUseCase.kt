package cy.volleybolley.games.domain.api.game

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.domain.model.event.game.JoinedGame

interface JoinGameUseCase {
    suspend fun joinGame(gameId: Int): VolleyResult<JoinedGame, ErrorType>
}
