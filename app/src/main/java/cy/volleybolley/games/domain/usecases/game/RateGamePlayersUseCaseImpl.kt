package cy.volleybolley.games.domain.usecases.game

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.domain.api.GamesRepository
import cy.volleybolley.games.domain.api.game.RateGamePlayersUseCase
import cy.volleybolley.games.domain.model.entity.RatePlayer

class RateGamePlayersUseCaseImpl(
    private val repository: GamesRepository
) : RateGamePlayersUseCase {
    override suspend fun ratePlayers(
        gameId: Int,
        players: List<RatePlayer>
    ): VolleyResult<Unit, ErrorType> {
        return repository.ratePlayers(gameId = gameId, players = players)
    }
}
