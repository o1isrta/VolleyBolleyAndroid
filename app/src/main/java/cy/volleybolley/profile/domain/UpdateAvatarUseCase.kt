package cy.volleybolley.profile.domain

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.profile.domain.api.ProfileRepository

class UpdateAvatarUseCase(
    private val repository: ProfileRepository,
) {
    /**
     * Если при удачном запросе вам пришла в ответ пустая строка (тем более при запросе с
     * avatarBase64String = null), то это значит, что вы удалили аватар.
     */
    suspend fun execute(accessToken: String?, avatarBase64String: String?): VolleyResult<String, ErrorType> {
        return repository.updateAvatar(accessToken, avatarBase64String)
    }
}
