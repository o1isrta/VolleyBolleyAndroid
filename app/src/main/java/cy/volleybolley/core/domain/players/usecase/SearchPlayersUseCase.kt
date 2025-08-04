package cy.volleybolley.core.domain.players.usecase

import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.core.domain.players.model.Player

fun interface SearchPlayersUseCase {
    suspend operator fun invoke(query: String): VolleyResult<List<Player>, Throwable>
}

