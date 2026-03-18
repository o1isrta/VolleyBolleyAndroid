package cy.volleybolley.auth.domain.api.storage

interface TokenStorage {
    suspend fun saveAccessToken(token: String)
    fun getAccessToken(): String?
    suspend fun saveRefreshToken(token: String)
    fun getRefreshToken(): String?
    suspend fun clear()

    fun hasRefreshToken(): Boolean
}
