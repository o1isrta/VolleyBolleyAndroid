package cy.volleybolley.auth.domain.api.usecase

interface GetAuthenticatedStatusUseCase {
    suspend fun execute(): Boolean
}
