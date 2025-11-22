package cy.volleybolley.auth.domain.api.usecase

import cy.volleybolley.profile.domain.model.PersonalData
import kotlinx.coroutines.flow.StateFlow

interface GetPersonalDataUseCase {
    fun execute(): StateFlow<PersonalData?>
}
