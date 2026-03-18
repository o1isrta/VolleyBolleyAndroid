package cy.volleybolley.auth.domain.impl.usecase

import cy.volleybolley.auth.domain.api.AuthRepository
import cy.volleybolley.auth.domain.api.LoginDataRepository
import cy.volleybolley.auth.domain.api.usecase.GoogleTokenAuthUseCase
import cy.volleybolley.auth.domain.models.AuthResult
import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.core.domain.model.mapSuccess
import cy.volleybolley.core.domain.model.onSuccess

class GoogleTokenAuthUseCaseImpl(
    private val authRepository: AuthRepository,
    private val loginDataRepository: LoginDataRepository
) : GoogleTokenAuthUseCase {
    override suspend fun execute(idToken: String): VolleyResult<AuthResult, ErrorType> {
        return authRepository.loginWithGoogle(idToken)
            .onSuccess { loginData ->
                loginDataRepository.saveLoginData(loginData)
            }
            .mapSuccess { loginData ->
                AuthResult(isRegistered = loginData.isRegistered)
            }
    }
}
