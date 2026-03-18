package cy.volleybolley.auth.domain.api.usecase

import cy.volleybolley.auth.domain.models.AuthResult
import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult

interface GoogleTokenAuthUseCase {
    suspend fun execute(idToken: String): VolleyResult<AuthResult, ErrorType>
}
