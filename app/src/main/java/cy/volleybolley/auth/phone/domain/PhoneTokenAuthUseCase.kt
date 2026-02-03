package cy.volleybolley.auth.phone.domain

import cy.volleybolley.auth.domain.models.LoginData
import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult

interface PhoneTokenAuthUseCase {
    suspend fun loginWithPhone(phone: String): VolleyResult<LoginData, ErrorType>
}
