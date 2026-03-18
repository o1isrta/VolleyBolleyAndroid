package cy.volleybolley.auth.domain.api

interface RefreshTokenTimestampStorage {
    suspend fun saveRefreshTokenTimestamp(timestamp: Long)
    suspend fun getRefreshTokenTimestamp(): Long?
    suspend fun clear()
}
