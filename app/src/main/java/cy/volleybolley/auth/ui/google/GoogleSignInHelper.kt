package cy.volleybolley.auth.ui.google

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.common.api.ApiException
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.model.VolleyUiUtil.showDebugLog
import cy.volleybolley.core.util.VolleyLog
import io.ktor.utils.io.CancellationException
import kotlinx.coroutines.tasks.await

class GoogleSignInHelper(context: Context) {
    private val oneTapClient = Identity.getSignInClient(context)
    private val clientId = context.getString(R.string.default_web_client_id)

    private val signInRequestAuthorized = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(clientId)
                .setFilterByAuthorizedAccounts(true)
                .build()
        )
        .setAutoSelectEnabled(false)
        .build()

    private val signInRequestAll = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(clientId)
                .setFilterByAuthorizedAccounts(false)
                .build()
        )
        .setAutoSelectEnabled(false)
        .build()

    suspend fun signIn(): IntentSender? {
        var intentSender: IntentSender? = null

        try {
            VolleyLog.v(TAG, "🚀 Trying with authorized accounts only...")

            intentSender = try {
                val result = oneTapClient.beginSignIn(signInRequestAuthorized).await()
                VolleyLog.v(TAG, "Success with authorized accounts")
                result.pendingIntent.intentSender
            } catch (e: ApiException) {
                VolleyLog.e(TAG, "No authorized accounts, trying all accounts...", e)

                val result = oneTapClient.beginSignIn(signInRequestAll).await()
                VolleyLog.v(TAG, "Success with all accounts")
                result.pendingIntent.intentSender
            }
        } catch (e: ApiException) {
            VolleyLog.e(TAG, "Both attempts failed: statusCode=${e.statusCode}, message=${e.message}", e)
        } catch (e: CancellationException) {
            throw e
        }

        return intentSender
    }

    fun extractGoogleIdToken(intent: Intent?): String? = try {
        showDebugLog(TAG, "Extracting ID token from intent...")
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val token = credential.googleIdToken

        if (token != null) {
            VolleyLog.v(TAG, "Token extracted successfully")
        } else {
            VolleyLog.v(TAG, "Token is null!")
        }

        token
    } catch (e: ApiException) {
        VolleyLog.e(TAG, "xtract failed: statusCode=${e.statusCode}, message=${e.message}", e)
        null
    }

    private companion object {
        val TAG = GoogleSignInHelper::class.simpleName.orEmpty()
    }
}
