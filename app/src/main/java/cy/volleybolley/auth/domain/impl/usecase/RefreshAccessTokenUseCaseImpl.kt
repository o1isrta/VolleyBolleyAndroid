package cy.volleybolley.auth.domain.impl.usecase

import cy.volleybolley.auth.domain.api.AuthRepository
import cy.volleybolley.auth.domain.api.storage.TokenStorage
import cy.volleybolley.auth.domain.api.usecase.RefreshAccessTokenUseCase
import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.core.domain.model.onSuccess

class RefreshAccessTokenUseCaseImpl(
    private val authRepository: AuthRepository,
    private val tokenStorage: TokenStorage
) : RefreshAccessTokenUseCase {
    override suspend fun execute(): VolleyResult<String, ErrorType> {
        val refreshToken = tokenStorage.getRefreshToken()
            ?: return VolleyResult.Failure(ErrorType.NO_REFRESH_TOKEN)

        return authRepository.refreshAccessToken(refreshToken)
            .onSuccess { newAccessToken ->
                tokenStorage.saveAccessToken(newAccessToken)
            }
    }
}
