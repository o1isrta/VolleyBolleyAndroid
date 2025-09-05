package cy.volleybolley.games.domain.api.game

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.domain.model.entity.PlayerShort

interface GetGamePlayersToRateUseCase {
    suspend fun getPlayers(gameId: Int): VolleyResult<List<PlayerShort>, ErrorType>
}
