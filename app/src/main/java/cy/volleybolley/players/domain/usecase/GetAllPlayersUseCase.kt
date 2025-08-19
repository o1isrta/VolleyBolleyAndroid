package cy.volleybolley.players.domain.usecase

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.players.domain.model.Player

fun interface GetAllPlayersUseCase {
    suspend operator fun invoke(): VolleyResult<List<Player>, ErrorType>
}
