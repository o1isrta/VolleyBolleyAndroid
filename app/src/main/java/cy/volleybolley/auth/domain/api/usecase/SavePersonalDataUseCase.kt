package cy.volleybolley.auth.domain.api.usecase

import cy.volleybolley.profile.domain.model.PersonalData

interface SavePersonalDataUseCase {
    suspend fun execute(personalData: PersonalData)
}
