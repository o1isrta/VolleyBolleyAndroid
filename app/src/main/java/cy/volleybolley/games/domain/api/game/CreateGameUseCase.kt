package cy.volleybolley.games.domain.api.game

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.domain.model.event.game.CreateGame
import cy.volleybolley.games.domain.model.event.game.CreatedGame

interface CreateGameUseCase {
    suspend fun createGame(game: CreateGame): VolleyResult<CreatedGame, ErrorType>
}
