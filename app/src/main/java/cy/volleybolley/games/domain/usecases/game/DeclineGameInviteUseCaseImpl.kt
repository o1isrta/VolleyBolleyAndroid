package cy.volleybolley.games.domain.usecases.game

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.domain.api.GameParticipationRepository
import cy.volleybolley.games.domain.api.game.DeclineGameInviteUseCase

class DeclineGameInviteUseCaseImpl(
    private val repository: GameParticipationRepository
) : DeclineGameInviteUseCase {
    override suspend fun declineGameInvite(gameId: Int): VolleyResult<Unit, ErrorType> {
        return repository.declineGameInvite(gameId)
    }
}
