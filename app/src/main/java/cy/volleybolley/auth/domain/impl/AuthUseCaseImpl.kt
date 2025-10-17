package cy.volleybolley.auth.domain.impl

import cy.volleybolley.auth.domain.AuthUseCase
import cy.volleybolley.auth.domain.AuthRepository
import cy.volleybolley.auth.domain.models.LoginData
import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult

class AuthUseCaseImpl(val authRepository: AuthRepository) : AuthUseCase {
    override suspend fun loginWithGoogle(idToken: String): VolleyResult<LoginData, ErrorType> {
        return authRepository.loginWithGoogle(idToken)
    }
}
