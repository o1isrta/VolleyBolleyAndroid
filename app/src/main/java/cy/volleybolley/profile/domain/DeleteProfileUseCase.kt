package cy.volleybolley.profile.domain

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.profile.domain.api.ProfileRepository

class DeleteProfileUseCase(
    private val repository: ProfileRepository,
) {
    suspend fun execute(accessToken: String?): VolleyResult<Unit, ErrorType> {
        return repository.deleteProfile(accessToken)
    }
}
