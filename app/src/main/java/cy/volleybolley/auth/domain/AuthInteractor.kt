package cy.volleybolley.auth.domain

import cy.volleybolley.auth.data.network.model.AuthResponse
import cy.volleybolley.core.data.network.model.Response

interface AuthInteractor {
    suspend fun loginWithGoogle(idToken: String): Response<AuthResponse>
}
