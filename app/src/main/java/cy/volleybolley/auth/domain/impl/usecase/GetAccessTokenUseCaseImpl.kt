package cy.volleybolley.auth.domain.impl.usecase

import cy.volleybolley.auth.domain.api.usecase.GetAccessTokenUseCase
import cy.volleybolley.auth.domain.api.LoginDataRepository

class GetAccessTokenUseCaseImpl(
    private val loginDataRepository: LoginDataRepository
) : GetAccessTokenUseCase {
    override suspend fun execute(): String? {
        return loginDataRepository.getAccessToken()
    }
}
