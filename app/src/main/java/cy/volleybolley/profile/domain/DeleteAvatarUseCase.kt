package cy.volleybolley.profile.domain

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.profile.domain.api.ProfileRepository

class DeleteAvatarUseCase(
    private val repository: ProfileRepository,
) {
    suspend fun execute(): VolleyResult<String, ErrorType> {
        return repository.updateAvatar(imageBytes = null)
    }
}
