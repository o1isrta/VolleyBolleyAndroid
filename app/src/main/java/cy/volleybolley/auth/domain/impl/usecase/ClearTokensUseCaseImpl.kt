package cy.volleybolley.auth.domain.impl.usecase

import cy.volleybolley.auth.domain.api.usecase.ClearTokensUseCase
import cy.volleybolley.auth.domain.api.LoginDataRepository

class ClearTokensUseCaseImpl(
    private val loginDataRepository: LoginDataRepository
) : ClearTokensUseCase {
    override suspend fun execute() {
        loginDataRepository.clearTokens()
    }
}
