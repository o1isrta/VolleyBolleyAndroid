package cy.volleybolley.games.domain.api.game

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.domain.model.event.Preview

interface GetGameScreenPreviewUseCase {
    suspend fun getPreview(): VolleyResult<Preview, ErrorType>
}
