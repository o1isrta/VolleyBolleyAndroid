package cy.volleybolley.auth.domain.impl.usecase

import cy.volleybolley.auth.domain.api.LoginDataRepository
import cy.volleybolley.auth.domain.api.usecase.GetRefreshTokenTimestampUseCase

class GetRefreshTokenTimestampUseCaseImpl(
    private val loginDataRepository: LoginDataRepository
) : GetRefreshTokenTimestampUseCase {
    override suspend fun execute(): Long? {
        return loginDataRepository.getRefreshTokenTimestamp()
    }
}
