package cy.volleybolley.auth.chooseMethod

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalResources
import cy.volleybolley.R
import cy.volleybolley.auth.chooseMethod.util.GoogleSignInHelper
import cy.volleybolley.auth.chooseMethod.util.GoogleSignInHelper.GoogleSignInResult
import kotlinx.coroutines.launch

/**
 * Encapsulates Google Sign-In logic using Credential Manager API.
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

    return {
        onSignInStarted()
        coroutineScope.launch {
            when (val result = googleSignInHelper.signIn(context)) {
                is GoogleSignInResult.Success -> onTokenReceived(result.idToken)
                is GoogleSignInResult.Cancelled -> onSignInFailed()
                is GoogleSignInResult.Failure -> {
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
}
