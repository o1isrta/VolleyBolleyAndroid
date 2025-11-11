package cy.volleybolley.auth.data

import android.content.Context
import androidx.core.content.edit
import cy.volleybolley.auth.domain.api.LoginDataRepository
import cy.volleybolley.profile.domain.model.PersonalData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.Json

class LoginDataRepositoryImpl(
    context: Context,
    private val json: Json
) : LoginDataRepository {
    companion object {
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
        private const val KEY_IS_REGISTERED = "is_registered"
        private const val KEY_PERSONAL_DATA = "personal_data"
        private const val APP_PREFS = "app_prefs"
    }

    private val sharedPrefs = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)

    // StateFlow for runtime refreshToken check and throw user to AuthScreen
    private val _isAuthenticated = MutableStateFlow(
        sharedPrefs.getString(KEY_REFRESH_TOKEN, null) != null
    )
    override val isAuthenticated: StateFlow<Boolean> = _isAuthenticated.asStateFlow()

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
    }

    override suspend fun getPersonalData(): PersonalData? {
        val personalDataJson = sharedPrefs.getString(KEY_PERSONAL_DATA, null)
        return personalDataJson?.let { json.decodeFromString<PersonalData>(it) }
    }

    override suspend fun clearAll() {
        sharedPrefs.edit {
            remove(KEY_ACCESS_TOKEN)
            remove(KEY_REFRESH_TOKEN)
            remove(KEY_IS_REGISTERED)
            remove(KEY_PERSONAL_DATA)
        }
        _isAuthenticated.value = false
    }
}
