package cy.volleybolley.core.domain.players.usecase

import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.core.domain.players.model.Player

fun interface GetAllPlayersUseCase {
    suspend operator fun invoke(): VolleyResult<List<Player>, Throwable>
}
