package cy.volleybolley.auth.domain.impl.usecase

import cy.volleybolley.auth.domain.api.RefreshTokenTimestampRepository
import cy.volleybolley.auth.domain.api.state.AuthStateHolder
import cy.volleybolley.auth.domain.api.storage.TokenStorage
import cy.volleybolley.auth.domain.api.usecase.ClearTokensUseCase

class ClearTokensUseCaseImpl(
    private val tokenStorage: TokenStorage,
    private val refreshTokenTimestampRepository: RefreshTokenTimestampRepository,
    private val authStateHolder: AuthStateHolder
) : ClearTokensUseCase {
    override suspend fun execute() {
        tokenStorage.clearTokens()
        refreshTokenTimestampRepository.clearRefreshTokenTimestamp()
        authStateHolder.setAuthenticated(false)
    }
}
