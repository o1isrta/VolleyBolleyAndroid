package cy.volleybolley.auth.ui

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.util.Log
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.common.api.ApiException
import io.ktor.utils.io.CancellationException
import kotlinx.coroutines.tasks.await

class GoogleSignInHelper(
    context: Context,
    clientId: String
) {
    private val oneTapClient = Identity.getSignInClient(context)

    private val signInRequest = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(clientId)
                .setFilterByAuthorizedAccounts(false)
                .build()
        )
        .setAutoSelectEnabled(true)
        .build()

    suspend fun launch(): IntentSender? = try {
        val result = oneTapClient.beginSignIn(signInRequest).await()
        result.pendingIntent.intentSender
    } catch (e: ApiException) {
        Log.w("SignIn", "Sign-in failed", e)
        null
    } catch (e: CancellationException) {
        Log.w("SignIn", "Sign-in failed", e)
        throw e
    }

    fun extractIdToken(intent: Intent?): String? = try {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        credential.googleIdToken
    } catch (e: ApiException) {
        Log.w("SignIn", "Sign-in failed", e)
        null
    } catch (e: CancellationException) {
        Log.w("SignIn", "Sign-in failed", e)
        throw e
    }
}
