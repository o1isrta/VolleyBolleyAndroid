package cy.volleybolley.core.domain.players.usecase

import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.core.domain.players.model.Player

fun interface AddToFavoritesUseCase {
    suspend operator fun invoke(playerId: Int): VolleyResult<Player, Throwable>
}
