package cy.volleybolley.profile.domain

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.profile.domain.api.ProfileRepository
import cy.volleybolley.profile.domain.model.PersonalData

class UpdatePersonalDataUseCase(
    private val repository: ProfileRepository,
) {
    /**
     * @param personalData поля этого параметра должны быть заполнены в соответсвии правилам:
     * 1. если String поле не будет обновляться, то значение должно быть пустой строкой
     * 2. если Int поле не будет обновляться, то значение должно быть -1
     */
    suspend fun execute(accessToken: String?, personalData: PersonalData): VolleyResult<Unit, ErrorType> {
        return repository.updatePersonalData(accessToken, personalData)
    }
}
