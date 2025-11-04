package cy.volleybolley.auth.domain.impl.usecase

import cy.volleybolley.auth.domain.api.LoginDataRepository
import cy.volleybolley.auth.domain.api.usecase.SaveRefreshTokenTimestampUseCase

class SaveRefreshTokenTimestampUseCaseImpl(
    private val loginDataRepository: LoginDataRepository
) : SaveRefreshTokenTimestampUseCase {
    override suspend fun execute(timestamp: Long) {
        loginDataRepository.saveRefreshTokenTimestamp(timestamp)
    }
}
