package cy.volleybolley.games.domain.usecases.game

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.domain.api.GamesRepository
import cy.volleybolley.games.domain.api.game.JoinGameUseCase
import cy.volleybolley.games.domain.model.event.game.JoinedGame

class JoinGameUseCaseImpl(private val repository: GamesRepository) : JoinGameUseCase {
    override suspend fun joinGame(gameId: Int): VolleyResult<JoinedGame, ErrorType> {
        return repository.joinGame(gameId)
    }
}
