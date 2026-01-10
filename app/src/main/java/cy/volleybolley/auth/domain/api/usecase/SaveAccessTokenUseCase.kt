package cy.volleybolley.auth.domain.api.usecase

interface SaveAccessTokenUseCase {
    suspend fun execute(accessToken: String)
}
