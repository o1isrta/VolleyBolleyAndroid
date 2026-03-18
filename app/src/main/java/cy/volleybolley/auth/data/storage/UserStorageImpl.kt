package cy.volleybolley.auth.data.storage

import android.content.SharedPreferences
import androidx.core.content.edit
import cy.volleybolley.auth.domain.api.storage.UserStorage
import cy.volleybolley.profile.domain.model.PersonalData
import kotlinx.serialization.json.Json

class UserStorageImpl(
    private val prefs: SharedPreferences,
    private val json: Json
) : UserStorage {

    companion object {
        private const val KEY_IS_REGISTERED = "is_registered"
        private const val KEY_PERSONAL_DATA = "personal_data"
    }

    override suspend fun savePersonalData(data: PersonalData) {
        prefs.edit { putString(KEY_PERSONAL_DATA, json.encodeToString(data)) }
    }

    override suspend fun getPersonalData(): PersonalData? {
        val dataJson = prefs.getString(KEY_PERSONAL_DATA, null) ?: return null
        return try {
            json.decodeFromString(dataJson)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun saveIsRegistered(isRegistered: Boolean) {
        prefs.edit { putBoolean(KEY_IS_REGISTERED, isRegistered) }
    }

    override suspend fun getIsRegistered(): Boolean {
        return prefs.getBoolean(KEY_IS_REGISTERED, false)
    }

    override suspend fun clearUserData() {
        prefs.edit {
            remove(KEY_IS_REGISTERED)
            remove(KEY_PERSONAL_DATA)
        }
    }
}
