package cy.volleybolley.auth.domain.api.usecase

interface SaveIsRegisteredUseCase {
    suspend fun execute(isRegistered: Boolean)
}
