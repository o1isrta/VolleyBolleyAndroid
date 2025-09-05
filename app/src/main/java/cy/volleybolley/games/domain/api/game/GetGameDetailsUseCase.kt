package cy.volleybolley.games.domain.api.game

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.domain.model.event.game.GameDetails

interface GetGameDetailsUseCase {
    suspend fun getGameDetails(gameId: Int): VolleyResult<GameDetails, ErrorType>
}
