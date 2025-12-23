package cy.volleybolley.auth.domain.impl.usecase

import cy.volleybolley.auth.domain.api.RefreshTokenTimestampRepository
import cy.volleybolley.auth.domain.api.usecase.SaveRefreshTokenTimestampUseCase

class SaveRefreshTokenTimestampUseCaseImpl(
    private val refreshTokenTimestampRepository: RefreshTokenTimestampRepository
) : SaveRefreshTokenTimestampUseCase {
    override suspend fun execute(timestamp: Long) {
        refreshTokenTimestampRepository.saveRefreshTokenTimestamp(timestamp)
    }
}
