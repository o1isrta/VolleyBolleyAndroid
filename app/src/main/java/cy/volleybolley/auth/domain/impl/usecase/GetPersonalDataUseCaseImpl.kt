package cy.volleybolley.auth.domain.impl.usecase

import cy.volleybolley.auth.domain.api.state.AuthStateHolder
import cy.volleybolley.auth.domain.api.usecase.GetPersonalDataUseCase
import cy.volleybolley.profile.domain.model.PersonalData
import kotlinx.coroutines.flow.StateFlow

class GetPersonalDataUseCaseImpl(
    private val authStateHolder: AuthStateHolder
) : GetPersonalDataUseCase {
    override fun execute(): StateFlow<PersonalData?> {
        return authStateHolder.personalData
    }
}
