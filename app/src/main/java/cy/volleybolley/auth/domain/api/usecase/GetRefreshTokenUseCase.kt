package cy.volleybolley.auth.domain.api.usecase

interface GetRefreshTokenUseCase {
    suspend fun execute(): String?
}
