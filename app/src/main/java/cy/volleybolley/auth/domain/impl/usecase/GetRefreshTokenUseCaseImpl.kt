package cy.volleybolley.auth.domain.impl.usecase

import cy.volleybolley.auth.domain.api.storage.TokenStorage
import cy.volleybolley.auth.domain.api.usecase.GetRefreshTokenUseCase

class GetRefreshTokenUseCaseImpl(
    private val tokenStorage: TokenStorage
) : GetRefreshTokenUseCase {
    override suspend fun execute(): String? {
        return tokenStorage.getRefreshToken()
    }
}
