package cy.volleybolley.games.domain.usecases.game

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.domain.api.GamesRepository
import cy.volleybolley.games.domain.api.game.GetGameDetailsUseCase
import cy.volleybolley.games.domain.model.event.game.GameDetails

class GetGameDetailsUseCaseImpl(
    private val repository: GamesRepository
) : GetGameDetailsUseCase {
    override suspend fun getGameDetails(gameId: Int): VolleyResult<GameDetails, ErrorType> {
        return repository.getGameDetails(gameId = gameId)
    }
}
