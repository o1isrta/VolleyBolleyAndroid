package cy.volleybolley.auth.data

import android.content.SharedPreferences
import androidx.core.content.edit
import cy.volleybolley.auth.domain.api.LoginDataRepository
import cy.volleybolley.auth.domain.api.RefreshTokenTimestampRepository
import cy.volleybolley.auth.domain.models.LoginData
import cy.volleybolley.profile.domain.model.PersonalData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.Json

class LoginDataRepositoryImpl(
    private val sharedPrefs: SharedPreferences,
    private val json: Json,
    private val refreshTokenTimestampRepository: RefreshTokenTimestampRepository
) : LoginDataRepository {
    companion object {
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
        private const val KEY_IS_REGISTERED = "is_registered"
        private const val KEY_PERSONAL_DATA = "personal_data"
    }

    // StateFlow for runtime refreshToken check and throw user to AuthScreen
    private val _isAuthenticated = MutableStateFlow(
        sharedPrefs.getString(KEY_REFRESH_TOKEN, null) != null
    )
    override val isAuthenticated: StateFlow<Boolean> = _isAuthenticated.asStateFlow()

    // StateFlow for runtime access to user personal data
    private val _personalData = MutableStateFlow(getPersonalData())
    override val personalData: StateFlow<PersonalData?> = _personalData.asStateFlow()

    // Tokens
    override suspend fun saveAccessToken(accessToken: String) {
        sharedPrefs.edit {
            putString(KEY_ACCESS_TOKEN, accessToken)
        }
    }

    override suspend fun saveRefreshToken(refreshToken: String) {
        sharedPrefs.edit {
            putString(KEY_REFRESH_TOKEN, refreshToken)
        }
        _isAuthenticated.value = true
    }

    override suspend fun getAccessToken(): String? {
        return sharedPrefs.getString(KEY_ACCESS_TOKEN, null)
    }

    override suspend fun getRefreshToken(): String? {
        return sharedPrefs.getString(KEY_REFRESH_TOKEN, null)
    }

    override suspend fun clearTokens() {
        sharedPrefs.edit {
            remove(KEY_ACCESS_TOKEN)
            remove(KEY_REFRESH_TOKEN)
        }
        _isAuthenticated.value = false
    }

    // Registration status
    override suspend fun saveIsRegistered(isRegistered: Boolean) {
        sharedPrefs.edit {
            putBoolean(KEY_IS_REGISTERED, isRegistered)
        }
    }

    override suspend fun getIsRegistered(): Boolean {
        return sharedPrefs.getBoolean(KEY_IS_REGISTERED, false)
    }

    // Personal Data
    override suspend fun savePersonalData(personalData: PersonalData) {
        val personalDataJson = json.encodeToString(personalData)
        sharedPrefs.edit {
            putString(KEY_PERSONAL_DATA, personalDataJson)
        }
        _personalData.value = personalData
    }

    private fun getPersonalData(): PersonalData? {
        val personalDataJson = sharedPrefs.getString(KEY_PERSONAL_DATA, null)
        return personalDataJson?.let { json.decodeFromString<PersonalData>(it) }
    }

    // Save all login data at once - single responsibility for complete login data persistence
    override suspend fun saveLoginData(loginData: LoginData) {
        saveAccessToken(loginData.accessToken)
        saveRefreshToken(loginData.refreshToken)
        savePersonalData(loginData.userPersonalData)
        saveIsRegistered(loginData.isRegistered)
        refreshTokenTimestampRepository.saveRefreshTokenTimestamp(System.currentTimeMillis())
    }

    override suspend fun clearAll() {
        sharedPrefs.edit {
            remove(KEY_ACCESS_TOKEN)
            remove(KEY_REFRESH_TOKEN)
            remove(KEY_IS_REGISTERED)
            remove(KEY_PERSONAL_DATA)
        }
        refreshTokenTimestampRepository.clearRefreshTokenTimestamp()
        _isAuthenticated.value = false
        _personalData.value = null
    }
}
