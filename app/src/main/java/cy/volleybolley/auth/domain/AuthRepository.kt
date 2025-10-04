package cy.volleybolley.auth.domain

import cy.volleybolley.auth.domain.models.AuthResponse
import cy.volleybolley.core.data.network.model.Response

interface AuthRepository {
    suspend fun loginWithGoogle(idToken: String): Response<AuthResponse>
}
