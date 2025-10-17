package cy.volleybolley.auth.domain

import cy.volleybolley.auth.domain.models.LoginData
import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult

interface AuthUseCase {
    suspend fun loginWithGoogle(idToken: String): VolleyResult<LoginData, ErrorType>
}
