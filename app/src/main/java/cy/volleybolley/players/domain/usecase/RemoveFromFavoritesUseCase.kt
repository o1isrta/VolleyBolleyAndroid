package cy.volleybolley.players.domain.usecase

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult

fun interface RemoveFromFavoritesUseCase {
    suspend operator fun invoke(playerId: Int): VolleyResult<Unit, ErrorType>
}
