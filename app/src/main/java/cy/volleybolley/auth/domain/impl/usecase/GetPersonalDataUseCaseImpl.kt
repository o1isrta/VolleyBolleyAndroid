package cy.volleybolley.auth.domain.impl.usecase

import cy.volleybolley.auth.domain.api.LoginDataRepository
import cy.volleybolley.auth.domain.api.usecase.GetPersonalDataUseCase
import cy.volleybolley.profile.domain.model.PersonalData
import kotlinx.coroutines.flow.StateFlow

class GetPersonalDataUseCaseImpl(
    private val loginDataRepository: LoginDataRepository
) : GetPersonalDataUseCase {
    override fun execute(): StateFlow<PersonalData?> {
        return loginDataRepository.personalData
    }
}
