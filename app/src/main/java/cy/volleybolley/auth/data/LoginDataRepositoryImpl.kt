package cy.volleybolley.auth.data

import android.content.Context
import androidx.core.content.edit
import cy.volleybolley.auth.domain.api.LoginDataRepository
import cy.volleybolley.profile.domain.model.PersonalData
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class LoginDataRepositoryImpl(
    context: Context,
    private val json: Json
) : LoginDataRepository {
    companion object {
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
        private const val KEY_PERSONAL_DATA = "personal_data"
        private const val APP_PREFS = "app_prefs"
    }

    private val sharedPrefs = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)

    // Токены
    override suspend fun saveTokens(accessToken: String, refreshToken: String) {
        sharedPrefs.edit {
            putString(KEY_ACCESS_TOKEN, accessToken)
            putString(KEY_REFRESH_TOKEN, refreshToken)
        }
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
    }

    // Персональные данные
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

    override suspend fun updatePersonalData(personalData: PersonalData) {
        savePersonalData(personalData)
    }

    override suspend fun clearPersonalData() {
        sharedPrefs.edit {
            remove(KEY_PERSONAL_DATA)
        }
    }

    // Полная очистка
    override suspend fun clearAll() {
        sharedPrefs.edit {
            remove(KEY_ACCESS_TOKEN)
            remove(KEY_REFRESH_TOKEN)
            remove(KEY_PERSONAL_DATA)
        }
    }
}
