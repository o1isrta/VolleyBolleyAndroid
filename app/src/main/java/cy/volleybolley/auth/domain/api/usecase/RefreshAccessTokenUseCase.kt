package cy.volleybolley.auth.domain.api.usecase

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult

interface RefreshAccessTokenUseCase {
    suspend fun execute(): VolleyResult<String, ErrorType>
}
