package cy.volleybolley.games.domain.api.game

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.domain.model.event.EventType

interface SkipRatingUseCase {
    suspend fun skipRating(id: Int, type: EventType): VolleyResult<Unit, ErrorType>
}
