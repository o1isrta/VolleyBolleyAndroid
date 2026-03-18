package cy.volleybolley.auth.domain.api.storage

interface TokenStorage {
    suspend fun saveAccessToken(token: String)
    suspend fun getAccessToken(): String?
    suspend fun saveRefreshToken(token: String)
    suspend fun getRefreshToken(): String?
    suspend fun clearTokens()

    fun hasRefreshToken(): Boolean
}
