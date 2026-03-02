package cy.volleybolley.auth.phone.domain.impl

import cy.volleybolley.auth.domain.api.AuthRepository
import cy.volleybolley.auth.domain.models.LoginData
import cy.volleybolley.auth.phone.domain.PhoneTokenAuthUseCase
import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult

class PhoneTokenAuthUseCaseImpl(private val authRepository: AuthRepository) : PhoneTokenAuthUseCase {
    override suspend fun loginWithPhone(phone: String): VolleyResult<LoginData, ErrorType> {
        return authRepository.loginWithPhone(phone)
    }
}
