package cy.volleybolley.auth.domain.impl.usecase

import cy.volleybolley.auth.domain.api.storage.TokenStorage
import cy.volleybolley.auth.domain.api.usecase.GetAccessTokenUseCase

class GetAccessTokenUseCaseImpl(
    private val tokenStorage: TokenStorage
) : GetAccessTokenUseCase {
    override suspend fun execute(): String? {
        return tokenStorage.getAccessToken()
    }
}
