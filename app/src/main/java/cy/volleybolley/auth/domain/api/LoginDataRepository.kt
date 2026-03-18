package cy.volleybolley.auth.domain.api

import cy.volleybolley.auth.domain.models.LoginData
import cy.volleybolley.profile.domain.model.PersonalData
import kotlinx.coroutines.flow.StateFlow

interface LoginDataRepository {
    // Auth state
    val isAuthenticated: StateFlow<Boolean>

    // PersonalData State
    val personalData: StateFlow<PersonalData?>

    // Tokens
    suspend fun saveAccessToken(accessToken: String)
    suspend fun saveRefreshToken(refreshToken: String)
    suspend fun getAccessToken(): String?
    suspend fun getRefreshToken(): String?
    suspend fun clearTokens()

    // Registration status
    suspend fun saveIsRegistered(isRegistered: Boolean)
    suspend fun getIsRegistered(): Boolean

    // Personal data
    suspend fun savePersonalData(personalData: PersonalData)

    // Save all login data at once
    suspend fun saveLoginData(loginData: LoginData)

    suspend fun clearAll()
}
