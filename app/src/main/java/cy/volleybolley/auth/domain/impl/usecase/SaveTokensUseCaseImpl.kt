package cy.volleybolley.auth.domain.impl.usecase

import cy.volleybolley.auth.domain.api.LoginDataRepository
import cy.volleybolley.auth.domain.api.usecase.SaveTokensUseCase

class SaveTokensUseCaseImpl(
    private val loginDataRepository: LoginDataRepository
) : SaveTokensUseCase {
    override suspend fun execute(accessToken: String, refreshToken: String) {
        loginDataRepository.saveTokens(accessToken, refreshToken)
    }
}
