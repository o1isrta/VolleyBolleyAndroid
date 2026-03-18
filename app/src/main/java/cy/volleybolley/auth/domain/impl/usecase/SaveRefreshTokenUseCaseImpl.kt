package cy.volleybolley.auth.domain.impl.usecase

import cy.volleybolley.auth.domain.api.state.AuthStateHolder
import cy.volleybolley.auth.domain.api.storage.TokenStorage
import cy.volleybolley.auth.domain.api.usecase.SaveRefreshTokenUseCase

class SaveRefreshTokenUseCaseImpl(
    private val tokenStorage: TokenStorage,
    private val authStateHolder: AuthStateHolder
) : SaveRefreshTokenUseCase {
    override suspend fun execute(refreshToken: String) {
        tokenStorage.saveRefreshToken(refreshToken)
        authStateHolder.setAuthenticated(true)
    }
}
