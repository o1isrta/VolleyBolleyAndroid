package cy.volleybolley.profile.domain

import cy.volleybolley.auth.domain.api.storage.UserStorage
import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.core.domain.model.onFailure
import cy.volleybolley.core.domain.model.onSuccess

class UpdatePersonalDataOnLaunchUseCase(
    private val userStorage: UserStorage,
    private val getPersonalDataFromServerUseCase: GetPersonalDataFromServerUseCase,
) {
    suspend fun execute(): VolleyResult<Unit, ErrorType> {
        var result: VolleyResult<Unit, ErrorType> = VolleyResult.Success(Unit)
        getPersonalDataFromServerUseCase.execute()
            .onSuccess { personalData ->
                userStorage.savePersonalData(personalData)
            }
            .onFailure { errorType ->
                result = VolleyResult.Failure(errorType)
            }
        return result
    }
}
