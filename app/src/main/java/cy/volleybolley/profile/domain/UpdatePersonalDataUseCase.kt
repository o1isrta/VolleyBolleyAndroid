package cy.volleybolley.profile.domain

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.profile.domain.api.ProfileRepository
import cy.volleybolley.profile.domain.model.PersonalData

class UpdatePersonalDataUseCase(
    private val repository: ProfileRepository,
) {
    suspend fun execute(accessToken: String?, personalData: PersonalData): VolleyResult<Unit, ErrorType> {
        return repository.updatePersonalData(accessToken, personalData)
    }
}
