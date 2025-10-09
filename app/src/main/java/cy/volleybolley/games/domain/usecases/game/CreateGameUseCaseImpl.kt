package cy.volleybolley.games.domain.usecases.game

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.domain.api.GamesRepository
import cy.volleybolley.games.domain.api.game.CreateGameUseCase
import cy.volleybolley.games.domain.model.event.game.CreateGame
import cy.volleybolley.games.domain.model.event.game.CreatedGame

class CreateGameUseCaseImpl(
    private val repository: GamesRepository,
) : CreateGameUseCase {
    override suspend fun createGame(game: CreateGame): VolleyResult<CreatedGame, ErrorType> {
        return repository.createGame(game)
    }
}
