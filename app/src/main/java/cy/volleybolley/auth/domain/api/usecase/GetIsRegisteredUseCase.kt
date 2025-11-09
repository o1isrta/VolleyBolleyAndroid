package cy.volleybolley.auth.domain.api.usecase

interface GetIsRegisteredUseCase {
    suspend fun execute(): Boolean
}
