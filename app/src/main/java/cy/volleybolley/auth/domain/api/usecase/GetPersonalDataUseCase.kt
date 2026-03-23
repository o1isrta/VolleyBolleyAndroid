package cy.volleybolley.auth.domain.api.usecase

import cy.volleybolley.profile.domain.model.PersonalData
import kotlinx.coroutines.flow.Flow

interface GetPersonalDataUseCase {
    suspend fun execute(): PersonalData?
    fun executeFlow(): Flow<PersonalData?>
}
