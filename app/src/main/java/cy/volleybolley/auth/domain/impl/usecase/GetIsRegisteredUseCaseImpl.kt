package cy.volleybolley.auth.domain.impl.usecase

import cy.volleybolley.auth.domain.api.storage.UserStorage
import cy.volleybolley.auth.domain.api.usecase.GetIsRegisteredUseCase

class GetIsRegisteredUseCaseImpl(
    private val userStorage: UserStorage
) : GetIsRegisteredUseCase {
    override suspend fun execute(): Boolean {
        return userStorage.getIsRegistered()
    }
}
