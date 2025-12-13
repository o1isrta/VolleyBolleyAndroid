package cy.volleybolley.auth.domain.api

import cy.volleybolley.auth.domain.models.LoginData
import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult

interface AuthRepository {
    suspend fun loginWithGoogle(idToken: String): VolleyResult<LoginData, ErrorType>
    suspend fun refreshAccessToken(refreshToken: String): VolleyResult<String, ErrorType>
}
