package cy.volleybolley.auth.ui

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import cy.volleybolley.auth.ui.presentation.AuthViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun AuthScreen(clientId: String) {
    val context = LocalContext.current
    val viewModel: AuthViewModel = koinViewModel()
    val googleHelper = remember { GoogleSignInHelper(context, clientId) }
    val coroutineScope = rememberCoroutineScope()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        val token = googleHelper.extractIdToken(result.data)
        viewModel.onTokenReceived(token)
    }

    LaunchedEffect(Unit) {
        viewModel.idToken.collect { token ->
            Log.d("ГуглТокен", token)
            delay(1000)
            viewModel.onTokenReceived(token)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = {
            coroutineScope.launch {
                val sender = googleHelper.launch()
                sender?.let {
                    launcher.launch(IntentSenderRequest.Builder(it).build())
                }
            }
        }) {
            Text("Continue with Google")
        }
    }
}
