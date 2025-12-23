package cy.volleybolley.auth.domain.impl.usecase

import cy.volleybolley.auth.domain.api.AuthRepository
import cy.volleybolley.auth.domain.api.usecase.GoogleTokenAuthUseCase
import cy.volleybolley.auth.domain.models.LoginData
import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult

class GoogleTokenAuthUseCaseImpl(val authRepository: AuthRepository) : GoogleTokenAuthUseCase {
    override suspend fun loginWithGoogle(idToken: String): VolleyResult<LoginData, ErrorType> {
        return authRepository.loginWithGoogle(idToken)
    }
}
