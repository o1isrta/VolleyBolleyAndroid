package cy.volleybolley.auth.domain.impl.usecase

import cy.volleybolley.auth.domain.api.storage.TokenStorage
import cy.volleybolley.auth.domain.api.usecase.GetAuthenticatedStatusUseCase

class GetAuthenticatedStatusUseCaseImpl(
    private val tokenStorage: TokenStorage
) : GetAuthenticatedStatusUseCase {
    override suspend fun execute(): Boolean {
        return tokenStorage.hasRefreshToken()
    }
}
