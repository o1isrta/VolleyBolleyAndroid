package cy.volleybolley.games.domain.api.game

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult

interface SkipGameRatingUseCase {
    suspend fun skipRating(gameId: Int): VolleyResult<Unit, ErrorType>
}
