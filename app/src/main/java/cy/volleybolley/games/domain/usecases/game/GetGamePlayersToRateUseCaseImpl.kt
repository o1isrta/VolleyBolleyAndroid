package cy.volleybolley.games.domain.usecases.game

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.domain.api.GameRatingRepository
import cy.volleybolley.games.domain.api.game.GetGamePlayersToRateUseCase
import cy.volleybolley.games.domain.model.entity.PlayerShort

class GetGamePlayersToRateUseCaseImpl(
    private val repository: GameRatingRepository
) : GetGamePlayersToRateUseCase {
    override suspend fun getPlayers(gameId: Int): VolleyResult<List<PlayerShort>, ErrorType> {
        return repository.getPlayersToRate(gameId = gameId)
    }
}
