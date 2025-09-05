package cy.volleybolley.games.domain.usecases.game

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.domain.api.GamesRepository
import cy.volleybolley.games.domain.api.game.CancelGameUseCase

class CancelGameUseCaseImpl(
    private val repository: GamesRepository
) : CancelGameUseCase {
    override suspend fun cancelGame(gameId: Int): VolleyResult<Unit, ErrorType> {
        return repository.cancelGame(gameId = gameId)
    }

}
