package cy.volleybolley.profile.domain

import cy.volleybolley.auth.domain.api.storage.UserStorage
import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.core.domain.model.mapSuccess
import cy.volleybolley.core.domain.model.onSuccess

class UpdatePersonalDataOnLaunchUseCase(
    private val userStorage: UserStorage,
    private val getPersonalDataFromServerUseCase: GetPersonalDataFromServerUseCase,
) {
    suspend fun execute(): VolleyResult<Unit, ErrorType> {
        return getPersonalDataFromServerUseCase.execute()
            .onSuccess { personalData ->
                userStorage.savePersonalData(personalData)
            }.mapSuccess { }
    }
}
