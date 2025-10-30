package cy.volleybolley.auth.domain.impl.usecase

import cy.volleybolley.auth.domain.api.usecase.ClearAllLoginDataUseCase
import cy.volleybolley.auth.domain.api.LoginDataRepository

class ClearAllLoginDataUseCaseImpl(
    private val loginDataRepository: LoginDataRepository
) : ClearAllLoginDataUseCase {
    override suspend fun execute() {
        loginDataRepository.clearAll()
    }
}
