package cy.volleybolley.auth.domain.impl.usecase

import cy.volleybolley.auth.domain.api.LoginDataRepository
import cy.volleybolley.auth.domain.api.usecase.SaveAccessTokenUseCase

class SaveAccessTokenUseCaseImpl(
    private val loginDataRepository: LoginDataRepository
) : SaveAccessTokenUseCase {
    override suspend fun execute(accessToken: String) {
        loginDataRepository.saveAccessToken(accessToken)
    }
}
