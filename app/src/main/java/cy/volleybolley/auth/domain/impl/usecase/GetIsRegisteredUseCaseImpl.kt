package cy.volleybolley.auth.domain.impl.usecase

import cy.volleybolley.auth.domain.api.LoginDataRepository
import cy.volleybolley.auth.domain.api.usecase.GetIsRegisteredUseCase

class GetIsRegisteredUseCaseImpl(
    private val loginDataRepository: LoginDataRepository
) : GetIsRegisteredUseCase {
    override suspend fun execute(): Boolean {
        return loginDataRepository.getIsRegistered()
    }
}
