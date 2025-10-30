package cy.volleybolley.auth.domain.impl.usecase

import cy.volleybolley.auth.domain.api.usecase.GetPersonalDataUseCase
import cy.volleybolley.auth.domain.api.LoginDataRepository
import cy.volleybolley.profile.domain.model.PersonalData

class GetPersonalDataUseCaseImpl(
    private val loginDataRepository: LoginDataRepository
) : GetPersonalDataUseCase {
    override suspend fun execute(): PersonalData? {
        return loginDataRepository.getPersonalData()
    }
}
