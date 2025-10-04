package cy.volleybolley.auth.domain.impl

import cy.volleybolley.auth.domain.AuthInteractor
import cy.volleybolley.auth.domain.AuthRepository
import cy.volleybolley.auth.domain.models.AuthResponse
import cy.volleybolley.core.data.network.model.Response

class AuthInteractorImpl(val authRepository: AuthRepository): AuthInteractor {
    override suspend fun loginWithGoogle(idToken: String) : Response<AuthResponse> {
        return authRepository.loginWithGoogle(idToken)
    }
}
