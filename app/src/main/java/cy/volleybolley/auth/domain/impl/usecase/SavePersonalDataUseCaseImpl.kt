package cy.volleybolley.auth.domain.impl.usecase

import cy.volleybolley.auth.domain.api.usecase.SavePersonalDataUseCase
import cy.volleybolley.auth.domain.api.LoginDataRepository
import cy.volleybolley.profile.domain.model.PersonalData

class SavePersonalDataUseCaseImpl(
    private val loginDataRepository: LoginDataRepository
) : SavePersonalDataUseCase {
    override suspend fun execute(personalData: PersonalData) {
        loginDataRepository.savePersonalData(personalData)
    }
}
