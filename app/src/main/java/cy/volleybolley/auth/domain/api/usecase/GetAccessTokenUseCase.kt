package cy.volleybolley.auth.domain.api.usecase

interface GetAccessTokenUseCase {
    suspend fun execute(): String?
}
