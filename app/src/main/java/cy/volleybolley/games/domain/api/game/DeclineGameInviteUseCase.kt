package cy.volleybolley.games.domain.api.game

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult

interface DeclineGameInviteUseCase {
    suspend fun declineGameInvite(gameId: Int): VolleyResult<Unit, ErrorType>
}
