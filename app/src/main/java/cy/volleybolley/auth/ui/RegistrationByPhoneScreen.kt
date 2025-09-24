package cy.volleybolley.auth.ui

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cy.volleybolley.auth.ui.presentation.PhoneAuthEffect
import cy.volleybolley.auth.ui.presentation.PhoneAuthEvent
import cy.volleybolley.auth.ui.presentation.PhoneAuthViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegistrationByPhoneScreen(
    viewModel: PhoneAuthViewModel = koinViewModel(),
    onAuthorized: (String) -> Unit,
    onError: (String) -> Unit,
    phoneAuthHelper: PhoneAuthHelper,
    activityProvider: () -> Activity
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is PhoneAuthEffect.RequestPhoneVerification -> {
                    val activity = activityProvider()
                    if (effect.resendToken == null) {
                        phoneAuthHelper.startPhoneNumberVerification(
                            activity = activity,
                            phoneNumber = effect.phone,
                            viewModel = viewModel,
                            onIdTokenReceived = { viewModel.onIdTokenReceived(it) },
                            onError = { viewModel.onError(it) }
                        )
                    } else {
                        phoneAuthHelper.resendCode(
                            activity = activity,
                            phoneNumber = effect.phone,
                            token = effect.resendToken,
                            viewModel = viewModel,
                            onIdTokenReceived = { viewModel.onIdTokenReceived(it) },
                            onError = { viewModel.onError(it) }
                        )
                    }
                }

                is PhoneAuthEffect.PhoneAuth -> onAuthorized(effect.idToken)
                is PhoneAuthEffect.ShowError -> onError(activityProvider().getString(effect.messageRes))
                else -> {}
            }
        }
    }

    Scaffold { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            if (!state.isCodeSent) {

                PhoneStep(
                    phone = state.phoneNumber,
                    onPhoneChange = { viewModel.onPhoneChanged(it) },
                    onSend = { phone -> viewModel.obtainEvent(PhoneAuthEvent.SendCode(phone)) },
                    isLoading = state.isLoading
                )
            } else {

                CodeStep(
                    code = state.code,
                    onCodeChange = { viewModel.onCodeChanged(it) },
                    onVerify = { code -> viewModel.obtainEvent(PhoneAuthEvent.VerifyCode(code)) },
                    onResend = { viewModel.obtainEvent(PhoneAuthEvent.ResendCode) },
                    resendTimeout = state.resendTimeout,
                    isLoading = state.isLoading
                )
            }
        }
    }
}

@Composable
private fun PhoneStep(
    phone: String,
    onPhoneChange: (String) -> Unit,
    onSend: (String) -> Unit,
    isLoading: Boolean
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = phone,
            onValueChange = onPhoneChange,
            label = { Text("Phone number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onSend(phone) },
            enabled = !isLoading && phone.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Send code")
        }
    }
}

@Composable
private fun CodeStep(
    code: String,
    onCodeChange: (String) -> Unit,
    onVerify: (String) -> Unit,
    onResend: () -> Unit,
    resendTimeout: Int,
    isLoading: Boolean
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = code,
            onValueChange = onCodeChange,
            label = { Text("Enter code") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onVerify(code) },
            enabled = !isLoading && code.length >= 6,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Verify")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = onResend,
            enabled = resendTimeout == 0 && !isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (resendTimeout > 0) {
                Text("Resend code in 00:${resendTimeout.toString().padStart(2, '0')}")
            } else {
                Text("Resend code")
            }
        }
    }
}

