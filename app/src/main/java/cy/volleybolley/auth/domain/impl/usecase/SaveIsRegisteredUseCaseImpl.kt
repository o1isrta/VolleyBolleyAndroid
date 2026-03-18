package cy.volleybolley.auth.domain.impl.usecase

import cy.volleybolley.auth.domain.api.storage.UserStorage
import cy.volleybolley.auth.domain.api.usecase.SaveIsRegisteredUseCase

class SaveIsRegisteredUseCaseImpl(
    private val userStorage: UserStorage
) : SaveIsRegisteredUseCase {
    override suspend fun execute(isRegistered: Boolean) {
        userStorage.saveIsRegistered(isRegistered)
    }
}
