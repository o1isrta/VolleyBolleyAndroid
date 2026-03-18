package cy.volleybolley.auth.phone.domain.impl

import cy.volleybolley.auth.domain.api.AuthRepository
import cy.volleybolley.auth.domain.api.LoginDataRepository
import cy.volleybolley.auth.domain.models.AuthResult
import cy.volleybolley.auth.phone.domain.PhoneTokenAuthUseCase
import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.core.domain.model.mapSuccess
import cy.volleybolley.core.domain.model.onSuccess

class PhoneTokenAuthUseCaseImpl(
    private val authRepository: AuthRepository,
    private val loginDataRepository: LoginDataRepository
) : PhoneTokenAuthUseCase {
    override suspend fun loginWithPhone(phone: String): VolleyResult<AuthResult, ErrorType> {
        return authRepository.loginWithPhone(phone)
            .onSuccess { loginData ->
                loginDataRepository.saveLoginData(loginData)
            }
            .mapSuccess { loginData ->
                AuthResult(isRegistered = loginData.isRegistered)
            }
    }
}
