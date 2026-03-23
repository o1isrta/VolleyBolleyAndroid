package cy.volleybolley.auth.domain.impl.usecase

import cy.volleybolley.auth.domain.api.storage.UserStorage
import cy.volleybolley.auth.domain.api.usecase.SavePersonalDataUseCase
import cy.volleybolley.profile.domain.model.PersonalData

class SavePersonalDataUseCaseImpl(
    private val userStorage: UserStorage
) : SavePersonalDataUseCase {
    override suspend fun execute(personalData: PersonalData) {
        userStorage.savePersonalData(personalData)
    }
}
