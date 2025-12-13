package cy.volleybolley.auth.domain.impl.usecase

import cy.volleybolley.auth.domain.api.LoginDataRepository
import cy.volleybolley.auth.domain.api.RefreshTokenTimestampRepository
import cy.volleybolley.auth.domain.api.usecase.ClearAllLoginDataUseCase

class ClearAllLoginDataUseCaseImpl(
    private val loginDataRepository: LoginDataRepository,
    private val refreshTokenTimestampRepository: RefreshTokenTimestampRepository
) : ClearAllLoginDataUseCase {
    override suspend fun execute() {
        loginDataRepository.clearAll()
        refreshTokenTimestampRepository.clearRefreshTokenTimestamp()
    }
}
