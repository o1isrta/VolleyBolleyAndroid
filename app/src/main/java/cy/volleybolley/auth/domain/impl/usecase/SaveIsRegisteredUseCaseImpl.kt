package cy.volleybolley.auth.domain.impl.usecase

import cy.volleybolley.auth.domain.api.LoginDataRepository
import cy.volleybolley.auth.domain.api.usecase.SaveIsRegisteredUseCase

class SaveIsRegisteredUseCaseImpl(
    private val loginDataRepository: LoginDataRepository
) : SaveIsRegisteredUseCase {
    override suspend fun execute(isRegistered: Boolean) {
        loginDataRepository.saveIsRegistered(isRegistered)
    }
}
