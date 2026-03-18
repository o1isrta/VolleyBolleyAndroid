package cy.volleybolley.auth.domain.impl.usecase

import cy.volleybolley.auth.domain.api.storage.UserStorage
import cy.volleybolley.auth.domain.api.usecase.GetPersonalDataUseCase
import cy.volleybolley.profile.domain.model.PersonalData

class GetPersonalDataUseCaseImpl(
    private val userStorage: UserStorage
) : GetPersonalDataUseCase {
    override suspend fun execute(): PersonalData? {
        return userStorage.getPersonalData()
    }
}
