package cy.volleybolley.profile.domain

import cy.volleybolley.auth.domain.api.usecase.GetPersonalDataUseCase
import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.profile.domain.api.ProfileRepository
import cy.volleybolley.profile.domain.model.PersonalData

class UpdatePersonalDataUseCase(
    private val repository: ProfileRepository,
    private val getPersonalDataUseCase: GetPersonalDataUseCase,
) {
    suspend fun execute(newPersonalData: PersonalData): VolleyResult<Unit, ErrorType> {
        return repository.updatePersonalData(
            newPersonalData = newPersonalData,
            cachedPersonalData = getPersonalDataUseCase.execute()
        )
    }
}
