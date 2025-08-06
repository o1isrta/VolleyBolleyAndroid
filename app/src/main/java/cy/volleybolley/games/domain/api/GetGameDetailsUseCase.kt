package cy.volleybolley.games.domain.api

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.domain.model.GameDetails

interface GetGameDetailsUseCase {
    suspend fun getGameDetails(gameId: Int): VolleyResult<GameDetails, ErrorType>
}
