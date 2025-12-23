package cy.volleybolley.auth.domain.api.usecase

interface SaveRefreshTokenUseCase {
    suspend fun execute(refreshToken: String)
}
