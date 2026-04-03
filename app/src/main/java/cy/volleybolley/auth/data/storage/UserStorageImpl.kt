package cy.volleybolley.auth.data.storage

import android.content.SharedPreferences
import androidx.core.content.edit
import cy.volleybolley.auth.domain.api.storage.UserStorage
import cy.volleybolley.profile.domain.model.PersonalData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.Json

class UserStorageImpl(
    private val encryptedPrefs: SharedPreferences,
    private val json: Json
) : UserStorage {

    private val personalDataFlow = MutableStateFlow(loadPersonalDataFromPrefs())

    override suspend fun savePersonalData(data: PersonalData) {
        encryptedPrefs.edit { putString(KEY_PERSONAL_DATA, json.encodeToString(data)) }
        personalDataFlow.value = data
    }

    override suspend fun getPersonalData(): PersonalData? {
        return personalDataFlow.value
    }

    override fun getPersonalDataFlow(): Flow<PersonalData?> = personalDataFlow.asStateFlow()

    override suspend fun saveIsRegistered(isRegistered: Boolean) {
        encryptedPrefs.edit { putBoolean(KEY_IS_REGISTERED, isRegistered) }
    }

    override suspend fun getIsRegistered(): Boolean {
        return encryptedPrefs.getBoolean(KEY_IS_REGISTERED, false)
    }

    override suspend fun clear() {
        encryptedPrefs.edit {
            remove(KEY_IS_REGISTERED)
            remove(KEY_PERSONAL_DATA)
        }
        personalDataFlow.value = null
    }

    private companion object {
        const val KEY_IS_REGISTERED = "is_registered"
        const val KEY_PERSONAL_DATA = "personal_data"
    }

    private fun loadPersonalDataFromPrefs(): PersonalData? {
        val dataJson = encryptedPrefs.getString(KEY_PERSONAL_DATA, null) ?: return null
        return try {
            json.decodeFromString<PersonalData>(dataJson)
        } catch (_: Exception) {
            null
        }
    }
}
