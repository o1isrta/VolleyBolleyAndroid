package cy.volleybolley.auth.domain.api.usecase

interface GetRefreshTokenTimestampUseCase {
    suspend fun execute(): Long?
}
