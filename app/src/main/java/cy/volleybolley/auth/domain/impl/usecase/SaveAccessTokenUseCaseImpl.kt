package cy.volleybolley.auth.domain.impl.usecase

import cy.volleybolley.auth.domain.api.storage.TokenStorage
import cy.volleybolley.auth.domain.api.usecase.SaveAccessTokenUseCase

class SaveAccessTokenUseCaseImpl(
    private val tokenStorage: TokenStorage
) : SaveAccessTokenUseCase {
    override suspend fun execute(accessToken: String) {
        tokenStorage.saveAccessToken(accessToken)
    }
}
