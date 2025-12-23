package cy.volleybolley.auth.domain.impl.usecase

import cy.volleybolley.auth.domain.api.LoginDataRepository
import cy.volleybolley.auth.domain.api.RefreshTokenTimestampRepository
import cy.volleybolley.auth.domain.api.usecase.ClearTokensUseCase

class ClearTokensUseCaseImpl(
    private val loginDataRepository: LoginDataRepository,
    private val refreshTokenTimestampRepository: RefreshTokenTimestampRepository
) : ClearTokensUseCase {
    override suspend fun execute() {
        loginDataRepository.clearTokens()
        refreshTokenTimestampRepository.clearRefreshTokenTimestamp()
    }
}
