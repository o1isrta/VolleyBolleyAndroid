package cy.volleybolley.auth.domain.impl.usecase

import cy.volleybolley.auth.domain.api.LoginDataRepository
import cy.volleybolley.auth.domain.api.usecase.GetRefreshTokenUseCase

class GetRefreshTokenUseCaseImpl(
    private val loginDataRepository: LoginDataRepository
) : GetRefreshTokenUseCase {
    override suspend fun execute(): String? {
        return loginDataRepository.getRefreshToken()
    }
}
