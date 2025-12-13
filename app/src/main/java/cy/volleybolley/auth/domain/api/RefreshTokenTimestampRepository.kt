package cy.volleybolley.auth.domain.api

interface RefreshTokenTimestampRepository {
    suspend fun saveRefreshTokenTimestamp(timestamp: Long)
    suspend fun getRefreshTokenTimestamp(): Long?
    suspend fun clearRefreshTokenTimestamp()
}
