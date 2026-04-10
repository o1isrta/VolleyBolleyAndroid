package cy.volleybolley.players.domain.usecase

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.players.domain.model.Player

interface GetFavoritePlayersUseCase {
    suspend operator fun invoke(): VolleyResult<List<Player>, ErrorType>
}
