package cy.volleybolley.games.domain.usecases.game

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.domain.api.game.DeclineGameInviteUseCase
import cy.volleybolley.games.domain.api.GamesRepository

class DeclineGameInviteUseCaseImpl(
    private val repository: GamesRepository
) : DeclineGameInviteUseCase {
    override suspend fun declineGameInvite(gameId: Int): VolleyResult<Unit, ErrorType> {
        return repository.declineGameInvite(gameId)
    }
}
