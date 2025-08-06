package cy.volleybolley.games.domain

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.domain.api.GamesRepository
import cy.volleybolley.games.domain.api.GetGameDetailsUseCase
import cy.volleybolley.games.domain.model.GameDetails

class GetGameDetailsUseCaseImpl(
    private val repository: GamesRepository
): GetGameDetailsUseCase {
    override suspend fun getGameDetails(gameId: Int): VolleyResult<GameDetails, ErrorType> {
        return repository.getGameDetails(gameId = gameId)
    }
}
