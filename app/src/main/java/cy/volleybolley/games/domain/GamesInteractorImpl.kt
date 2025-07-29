package cy.volleybolley.games.domain

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.domain.api.GamesInteractor
import cy.volleybolley.games.domain.api.GamesRepository
import cy.volleybolley.games.domain.model.Game

class GamesInteractorImpl(
    private val repository: GamesRepository,
) : GamesInteractor {
    override suspend fun createGame(game: Game): VolleyResult<Game?, ErrorType> {
        return repository.createGame(game)
    }
}
