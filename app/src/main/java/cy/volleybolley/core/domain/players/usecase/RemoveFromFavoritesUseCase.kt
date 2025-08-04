package cy.volleybolley.core.domain.players.usecase

import cy.volleybolley.core.domain.model.VolleyResult

fun interface RemoveFromFavoritesUseCase {
    suspend operator fun invoke(playerId: Int): VolleyResult<Unit, Throwable>
}

