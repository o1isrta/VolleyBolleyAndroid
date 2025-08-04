package cy.volleybolley.core.domain.players.usecase

import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.core.domain.players.model.PlayerDetail

fun interface GetPlayerDetailUseCase {
    suspend operator fun invoke(playerId: Int): VolleyResult<PlayerDetail, Throwable>
}
