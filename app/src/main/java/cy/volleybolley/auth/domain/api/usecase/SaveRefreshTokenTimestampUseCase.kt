package cy.volleybolley.auth.domain.api.usecase

interface SaveRefreshTokenTimestampUseCase {
    suspend fun execute(timestamp: Long)
}
