package cy.volleybolley.auth.data.storage

import android.content.SharedPreferences
import androidx.core.content.edit
import cy.volleybolley.auth.domain.api.storage.TokenStorage

class TokenStorageImpl(
    private val encryptedPrefs: SharedPreferences
) : TokenStorage {

    companion object {
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
    }

    override suspend fun saveAccessToken(token: String) {
        encryptedPrefs.edit { putString(KEY_ACCESS_TOKEN, token) }
    }

    override suspend fun getAccessToken(): String? {
        return encryptedPrefs.getString(KEY_ACCESS_TOKEN, null)
    }

    override suspend fun saveRefreshToken(token: String) {
        encryptedPrefs.edit { putString(KEY_REFRESH_TOKEN, token) }
    }

    override suspend fun getRefreshToken(): String? {
        return encryptedPrefs.getString(KEY_REFRESH_TOKEN, null)
    }

    override suspend fun clearTokens() {
        encryptedPrefs.edit {
            remove(KEY_ACCESS_TOKEN)
            remove(KEY_REFRESH_TOKEN)
        }
    }

    override fun hasRefreshToken(): Boolean {
        return encryptedPrefs.getString(KEY_REFRESH_TOKEN, null) != null
    }
}
