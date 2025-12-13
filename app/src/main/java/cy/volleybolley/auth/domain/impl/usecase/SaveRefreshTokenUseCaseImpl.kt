package cy.volleybolley.auth.domain.impl.usecase

import cy.volleybolley.auth.domain.api.LoginDataRepository
import cy.volleybolley.auth.domain.api.usecase.SaveRefreshTokenUseCase

class SaveRefreshTokenUseCaseImpl(
    private val loginDataRepository: LoginDataRepository
) : SaveRefreshTokenUseCase {
    override suspend fun execute(refreshToken: String) {
        loginDataRepository.saveRefreshToken(refreshToken)
    }
}
