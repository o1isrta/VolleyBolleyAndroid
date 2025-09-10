package cy.volleybolley.games.domain.api.game

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.domain.model.entity.PlayerShort
import cy.volleybolley.games.domain.model.event.EventType

interface GetPlayersToRateUseCase {
    suspend fun getPlayers(id: Int, type: EventType): VolleyResult<List<PlayerShort>, ErrorType>
}
