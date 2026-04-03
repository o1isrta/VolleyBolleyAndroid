package cy.volleybolley.auth.data

import android.content.SharedPreferences
import androidx.core.content.edit
import cy.volleybolley.auth.domain.api.RefreshTokenTimestampStorage

class RefreshTokenTimestampStorageImpl(
    private val encryptedPrefs: SharedPreferences
) : RefreshTokenTimestampStorage {

    override suspend fun saveRefreshTokenTimestamp(timestamp: Long) {
        encryptedPrefs.edit {
            putLong(KEY_REFRESH_TOKEN_TIMESTAMP, timestamp)
        }
    }

    override suspend fun getRefreshTokenTimestamp(): Long? {
        val timestamp = encryptedPrefs.getLong(KEY_REFRESH_TOKEN_TIMESTAMP, -1L)
        return if (timestamp == -1L) null else timestamp
    }

    override suspend fun clear() {
        encryptedPrefs.edit {
            remove(KEY_REFRESH_TOKEN_TIMESTAMP)
        }
    }

    private companion object {
        const val KEY_REFRESH_TOKEN_TIMESTAMP = "refresh_token_timestamp"
    }
}
