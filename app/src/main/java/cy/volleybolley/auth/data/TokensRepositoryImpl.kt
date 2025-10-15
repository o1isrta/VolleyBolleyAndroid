package cy.volleybolley.auth.data

import android.content.Context
import androidx.core.content.edit
import cy.volleybolley.auth.domain.TokensRepository

class TokensRepositoryImpl(context: Context) : TokensRepository {
    companion object {
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
        private const val APP_PREFS = "app_prefs"
    }

    private val sharedPrefs = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)

    override suspend fun saveTokens(accessToken: String, refreshToken: String) {
        sharedPrefs.edit {
            putString(KEY_ACCESS_TOKEN, accessToken)
                .putString(KEY_REFRESH_TOKEN, refreshToken)
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
                .remove(KEY_REFRESH_TOKEN)
        }
    }
}
