package cy.volleybolley.auth.domain.impl.usecase

import cy.volleybolley.auth.domain.api.RefreshTokenTimestampRepository
import cy.volleybolley.auth.domain.api.usecase.GetRefreshTokenTimestampUseCase

class GetRefreshTokenTimestampUseCaseImpl(
    private val refreshTokenTimestampRepository: RefreshTokenTimestampRepository
) : GetRefreshTokenTimestampUseCase {
    override suspend fun execute(): Long? {
        return refreshTokenTimestampRepository.getRefreshTokenTimestamp()
    }
}
