package cy.volleybolley.games.domain.api.game

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.domain.model.entity.RatePlayer

interface RateGamePlayersUseCase {
    suspend fun ratePlayers(gameId: Int, players: List<RatePlayer>): VolleyResult<Unit, ErrorType>
}
