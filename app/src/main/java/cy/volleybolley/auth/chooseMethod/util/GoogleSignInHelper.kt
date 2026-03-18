package cy.volleybolley.auth.chooseMethod.util

import android.content.Context
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import cy.volleybolley.R
import cy.volleybolley.core.util.VolleyLog
import io.ktor.utils.io.CancellationException

class GoogleSignInHelper(context: Context) {
    private val credentialManager = CredentialManager.Companion.create(context)
    private val clientId = context.getString(R.string.default_web_client_id)
    private val googleIdOptionAuthorized = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(true)
        .setServerClientId(clientId)
        .setAutoSelectEnabled(false)
        .build()
    private val googleIdOptionAll = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(false)
        .setServerClientId(clientId)
        .setAutoSelectEnabled(false)
        .build()

    suspend fun signIn(activityContext: Context): GoogleSignInResult {
        return try {
            VolleyLog.v(TAG, "Trying with authorized accounts only...")

            val authorizedRequest = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOptionAuthorized)
                .build()

            try {
                val result = credentialManager.getCredential(activityContext, authorizedRequest)
                VolleyLog.v(TAG, "Success with authorized accounts")
                extractTokenFromCredential(result.credential)
            } catch (e: NoCredentialException) {
                VolleyLog.v(TAG, "No authorized accounts, trying all accounts...", e)

                val allAccountsRequest = GetCredentialRequest.Builder()
                    .addCredentialOption(googleIdOptionAll)
                    .build()

                val result = credentialManager.getCredential(activityContext, allAccountsRequest)
                VolleyLog.v(TAG, "Success with all accounts")
                extractTokenFromCredential(result.credential)
            }
        } catch (e: GetCredentialCancellationException) {
            VolleyLog.v(TAG, "User cancelled sign-in", e)
            GoogleSignInResult.Cancelled
        } catch (e: GetCredentialException) {
            VolleyLog.e(TAG, "Sign-in failed: ${e.javaClass.simpleName}, message=${e.message}", e)
            GoogleSignInResult.Failure
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            VolleyLog.e(TAG, "Unexpected error: ${e.message}", e)
            GoogleSignInResult.Failure
        }
    }

    private fun extractTokenFromCredential(credential: Credential): GoogleSignInResult {
        return try {
            val googleIdTokenCredential = GoogleIdTokenCredential.Companion.createFrom(credential.data)
            VolleyLog.v(TAG, "Token extracted successfully")
            GoogleSignInResult.Success(googleIdTokenCredential.idToken)
        } catch (e: Exception) {
            VolleyLog.e(TAG, "Token extraction failed: ${e.message}", e)
            GoogleSignInResult.Failure
        }
    }

    sealed interface GoogleSignInResult {
        data class Success(val idToken: String) : GoogleSignInResult
        data object Cancelled : GoogleSignInResult
        data object Failure : GoogleSignInResult
    }

    private companion object {
        val TAG = GoogleSignInHelper::class.simpleName.orEmpty()
    }
}
