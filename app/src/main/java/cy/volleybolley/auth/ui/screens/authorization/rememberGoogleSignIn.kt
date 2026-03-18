package cy.volleybolley.auth.ui.screens.authorization

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalResources
import cy.volleybolley.R
import cy.volleybolley.auth.ui.google.GoogleSignInHelper
import kotlinx.coroutines.launch

/**
 * Encapsulates Google Sign-In logic.
 *
 * @param onSignInStarted Called when sign-in flow starts
 * @param onTokenReceived Called when Google ID token is successfully retrieved
 * @param onSignInFailed Called when sign-in fails (user cancellation is ignored)
 * @return A callback to trigger Google Sign-In flow
 */
@Composable
fun rememberGoogleSignIn(
    onSignInStarted: () -> Unit,
    onTokenReceived: (String) -> Unit,
    onSignInFailed: () -> Unit
): () -> Unit {
    val context = LocalContext.current
    val resources = LocalResources.current
    val coroutineScope = rememberCoroutineScope()
    val googleSignInHelper = remember { GoogleSignInHelper(context) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        when (result.resultCode) {
            Activity.RESULT_OK -> {
                val token = googleSignInHelper.extractGoogleIdToken(result.data)
                if (token != null) {
                    onTokenReceived(token)
                } else {
                    onSignInFailed()
                    Toast.makeText(
                        context,
                        resources.getString(R.string.auth_error_no_google_acc_on_device),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            Activity.RESULT_CANCELED -> { /* User canceled - nothing to do */ }

            else -> onSignInFailed()
        }
    }

    return {
        onSignInStarted()
        coroutineScope.launch {
            val intentSender = googleSignInHelper.signIn()
            if (intentSender != null) {
                launcher.launch(IntentSenderRequest.Builder(intentSender).build())
            } else {
                onSignInFailed()
                Toast.makeText(
                    context,
                    resources.getString(R.string.auth_error_no_google_acc_on_device),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
