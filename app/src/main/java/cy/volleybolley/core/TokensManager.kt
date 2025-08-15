package cy.volleybolley.core

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object TokensManager {
    private const val PREFS_NAME = "session_prefs"
    private const val KEY_ACCESS_TOKEN = "access_token"
    private const val KEY_REFRESH_TOKEN = "refresh_token"
    private const val KEY_REFRESH_TOKEN_UPDATED_TIME = "refresh_token_updated_time"

    private var _accessToken: String? = null
    private var _refreshToken: String? = null
    private var _refreshTokenLastUpdatedTime: Long? = null
    private lateinit var sharedPreferences: SharedPreferences

    var accessToken: String?
        get() = _accessToken
        set(value) {
            _accessToken = value
            sharedPreferences.edit { putString(KEY_ACCESS_TOKEN, value) }
        }
    var refreshToken: String?
        get() = _refreshToken
        set(value) {
            _refreshToken = value
            _refreshTokenLastUpdatedTime = System.currentTimeMillis()
            sharedPreferences.edit().apply {
                putString(KEY_REFRESH_TOKEN, value)
                putLong(KEY_REFRESH_TOKEN_UPDATED_TIME, _refreshTokenLastUpdatedTime ?: -1)
                apply()
            }
        }
    var refreshTokenLastUpdatedTime: Long? = null
        get() = _refreshTokenLastUpdatedTime
        private set

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        _accessToken = sharedPreferences.getString(KEY_ACCESS_TOKEN, null)
        _refreshToken = sharedPreferences.getString(KEY_REFRESH_TOKEN, null)
        val updatedTime = sharedPreferences.getLong(KEY_REFRESH_TOKEN_UPDATED_TIME, -1)
        _refreshTokenLastUpdatedTime = if (updatedTime != -1L) updatedTime else null
    }

    fun clear() {
        _accessToken = null
        _refreshToken = null
        _refreshTokenLastUpdatedTime = null
        sharedPreferences.edit { clear() }
    }
}
