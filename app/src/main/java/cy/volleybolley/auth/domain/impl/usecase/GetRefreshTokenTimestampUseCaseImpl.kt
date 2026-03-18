package cy.volleybolley.auth.domain.impl.usecase

import cy.volleybolley.auth.domain.api.RefreshTokenTimestampStorage
import cy.volleybolley.auth.domain.api.usecase.GetRefreshTokenTimestampUseCase

class GetRefreshTokenTimestampUseCaseImpl(
    private val refreshTokenTimestampStorage: RefreshTokenTimestampStorage
) : GetRefreshTokenTimestampUseCase {
    override suspend fun execute(): Long? {
        return refreshTokenTimestampStorage.getRefreshTokenTimestamp()
    }
}
