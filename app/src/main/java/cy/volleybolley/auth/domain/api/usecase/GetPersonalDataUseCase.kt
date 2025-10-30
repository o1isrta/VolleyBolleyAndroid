package cy.volleybolley.auth.domain.api.usecase

import cy.volleybolley.profile.domain.model.PersonalData

interface GetPersonalDataUseCase {
    suspend fun execute(): PersonalData?
}
