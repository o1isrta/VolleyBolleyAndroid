package cy.volleybolley.profile.domain

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.profile.domain.api.ProfileRepository
import cy.volleybolley.profile.domain.model.PersonalData

class GetPersonalDataUseCase(
    private val repository: ProfileRepository,
) {
    suspend fun execute(): VolleyResult<PersonalData, ErrorType> {
        return repository.getPersonalData()
    }
}
