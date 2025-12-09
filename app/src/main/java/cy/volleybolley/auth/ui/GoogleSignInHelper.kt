package cy.volleybolley.auth.ui

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.common.api.ApiException
import cy.volleybolley.BuildConfig
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.model.VolleyUiUtil.showDebugExceptionLog
import cy.volleybolley.core.presentation.ui.model.VolleyUiUtil.showDebugLog
import io.ktor.utils.io.CancellationException
import kotlinx.coroutines.tasks.await

class GoogleSignInHelper(
    context: Context,
) {
    private val oneTapClient = Identity.getSignInClient(context)
    private val clientId = context.getString(R.string.default_web_client_id)

    init {
        if (BuildConfig.DEBUG) {
            showDebugLog(TAG, "🔑 Client ID: $clientId")
            showDebugLog(TAG, "📦 Package: ${context.packageName}")
        }
    }

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
            showDebugLog(TAG, "🚀 Trying with authorized accounts only...")

            intentSender = try {
                val result = oneTapClient.beginSignIn(signInRequestAuthorized).await()
                showDebugLog(TAG, "✅ Success with authorized accounts")
                result.pendingIntent.intentSender
            } catch (e: ApiException) {
                showDebugExceptionLog(TAG, "⚠️ No authorized accounts, trying all accounts...", e)

                val result = oneTapClient.beginSignIn(signInRequestAll).await()
                showDebugLog(TAG, "✅ Success with all accounts")
                result.pendingIntent.intentSender
            }
        } catch (e: ApiException) {
            showDebugExceptionLog(TAG, "❌ Both attempts failed: statusCode=${e.statusCode}, message=${e.message}", e)
        } catch (e: CancellationException) {
            showDebugExceptionLog(TAG, "⚠️ Cancelled", e)
            throw e
        }

        return intentSender
    }

    fun extractGoogleIdToken(intent: Intent?): String? = try {
        showDebugLog(TAG, "📥 Extracting ID token from intent...")
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val token = credential.googleIdToken

        if (token != null) {
            showDebugLog(TAG, "✅ Token extracted successfully")
            showDebugLog(TAG, "Token preview: ${token.take(TOKEN_PREVIEW_LENGTH)}...")
        } else {
            showDebugLog(TAG, "❌ Token is null!")
        }

        token
    } catch (e: ApiException) {
        showDebugExceptionLog(TAG, "❌ Extract failed: statusCode=${e.statusCode}, message=${e.message}", e)
        null
    }

    companion object {
        private const val TAG = "GoogleSignInHelper"
        private const val TOKEN_PREVIEW_LENGTH = 30
    }
}
