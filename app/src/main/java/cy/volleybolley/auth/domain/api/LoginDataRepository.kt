package cy.volleybolley.auth.domain.api

import cy.volleybolley.profile.domain.model.PersonalData
import kotlinx.coroutines.flow.StateFlow

interface LoginDataRepository {
    // Auth state
    val isAuthenticated: StateFlow<Boolean>

    // Tokens
    suspend fun saveAccessToken(accessToken: String)
    suspend fun saveRefreshToken(refreshToken: String)
    suspend fun getAccessToken(): String?
    suspend fun getRefreshToken(): String?
    suspend fun clearTokens()

    // Timestamp for Refresh Token
    suspend fun saveRefreshTokenTimestamp(timestamp: Long)
    suspend fun getRefreshTokenTimestamp(): Long?

    // Registration status
    suspend fun saveIsRegistered(isRegistered: Boolean)
    suspend fun getIsRegistered(): Boolean

    // Personal data
    suspend fun savePersonalData(personalData: PersonalData)
    suspend fun getPersonalData(): PersonalData?
    suspend fun clearPersonalData()

    suspend fun clearAll()
}
