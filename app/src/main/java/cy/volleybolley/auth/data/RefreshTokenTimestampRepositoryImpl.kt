package cy.volleybolley.auth.data

import android.content.Context
import androidx.core.content.edit
import cy.volleybolley.auth.domain.api.RefreshTokenTimestampRepository

class RefreshTokenTimestampRepositoryImpl(context: Context) : RefreshTokenTimestampRepository {
    companion object {
        private const val KEY_REFRESH_TOKEN_TIMESTAMP = "refresh_token_timestamp"
        private const val APP_PREFS = "app_prefs"
    }

    private val sharedPrefs = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)

    override suspend fun saveRefreshTokenTimestamp(timestamp: Long) {
        sharedPrefs.edit {
            putLong(KEY_REFRESH_TOKEN_TIMESTAMP, timestamp)
        }
    }

    override suspend fun getRefreshTokenTimestamp(): Long? {
        val timestamp = sharedPrefs.getLong(KEY_REFRESH_TOKEN_TIMESTAMP, -1L)
        return if (timestamp == -1L) null else timestamp
    }

    override suspend fun clearRefreshTokenTimestamp() {
        sharedPrefs.edit {
            remove(KEY_REFRESH_TOKEN_TIMESTAMP)
        }
    }
}
