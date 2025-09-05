package cy.volleybolley.games.domain.usecases.game

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.domain.api.GamesRepository
import cy.volleybolley.games.domain.api.game.SkipGameRatingUseCase

class SkipGameRatingUseCaseImpl(
    private val repository: GamesRepository
): SkipGameRatingUseCase {
    override suspend fun skipRating(gameId: Int): VolleyResult<Unit, ErrorType> {
        return repository.skipRating(gameId = gameId)
    }
}
