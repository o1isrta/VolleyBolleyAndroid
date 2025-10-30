package cy.volleybolley.auth.domain.api.usecase

interface SaveTokensUseCase {
    suspend fun execute(accessToken: String, refreshToken: String)
}
