package cy.volleybolley.notification.data

import android.content.SharedPreferences
import cy.volleybolley.notification.domain.api.FCMTokenStore
import cy.volleybolley.notification.utils.Constants.FCM_TOKEN

class FCMTokenStoreImpl(
    private val prefs: SharedPreferences
) : FCMTokenStore {
    override fun saveToken(token: String) {
        prefs.edit().putString(FCM_TOKEN, token).commit()
    }

    override fun getToken(): String? = prefs.getString(FCM_TOKEN, null)
}
