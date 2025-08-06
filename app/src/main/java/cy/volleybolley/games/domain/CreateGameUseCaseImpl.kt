package cy.volleybolley.games.domain

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.domain.api.CreateGameUseCase
import cy.volleybolley.games.domain.api.GamesRepository
import cy.volleybolley.games.domain.model.Game

class CreateGameUseCaseImpl(
    private val repository: GamesRepository,
) : CreateGameUseCase {
    override suspend fun createGame(game: Game): VolleyResult<Game, ErrorType> {
        return repository.createGame(game)
    }
}
