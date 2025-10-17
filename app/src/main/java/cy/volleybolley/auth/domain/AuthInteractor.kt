package cy.volleybolley.auth.domain

import cy.volleybolley.auth.domain.models.LoginData
import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult

interface AuthInteractor {
    suspend fun loginWithGoogle(idToken: String): VolleyResult<LoginData, ErrorType>
}
