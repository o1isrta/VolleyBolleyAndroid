package cy.volleybolley.auth.data.storage

import android.content.SharedPreferences
import androidx.core.content.edit
import cy.volleybolley.auth.domain.api.storage.TokenStorage

class TokenStorageImpl(
    private val encryptedPrefs: SharedPreferences
) : TokenStorage {
    override suspend fun saveAccessToken(token: String) {
        encryptedPrefs.edit { putString(KEY_ACCESS_TOKEN, token) }
    }

    override fun getAccessToken(): String? {
        return encryptedPrefs.getString(KEY_ACCESS_TOKEN, null)
    }

    override suspend fun saveRefreshToken(token: String) {
        encryptedPrefs.edit { putString(KEY_REFRESH_TOKEN, token) }
    }

    override fun getRefreshToken(): String? {
        return encryptedPrefs.getString(KEY_REFRESH_TOKEN, null)
    }

    override suspend fun clear() {
        encryptedPrefs.edit {
            remove(KEY_ACCESS_TOKEN)
            remove(KEY_REFRESH_TOKEN)
        }
    }

    override fun hasRefreshToken(): Boolean {
        return encryptedPrefs.getString(KEY_REFRESH_TOKEN, null) != null
    }

    private companion object {
        const val KEY_ACCESS_TOKEN = "access_token"
        const val KEY_REFRESH_TOKEN = "refresh_token"
    }
}
