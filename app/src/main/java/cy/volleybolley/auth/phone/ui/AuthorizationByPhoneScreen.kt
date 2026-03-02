package cy.volleybolley.auth.phone.ui

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cy.volleybolley.auth.phone.ui.presentation.AuthorizationByPhoneViewModel
import cy.volleybolley.auth.phone.ui.presentation.VolleyballProgressIndicator
import cy.volleybolley.auth.phone.ui.presentation.model.AuthorizationByPhoneEffect
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun AuthorizationByPhoneScreen(
    viewModel: AuthorizationByPhoneViewModel = koinViewModel(),
    phoneAuthHelper: PhoneAuthHelper = koinInject(),
    paddingFromSystemUi: PaddingValues,
    onBackNavigationRequested: () -> Unit,
    onSuccessGetNotRegisterUser: (String) -> Unit,
    onSuccessGetRegisterUser: () -> Unit,
) {
    val context = LocalContext.current
    val activity = context as Activity

    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val effect by viewModel.uiEffect.collectAsStateWithLifecycle(null)

    LaunchedEffect(effect) {
        when (val e = effect) {
            is AuthorizationByPhoneEffect.RequestSendCode -> {
                phoneAuthHelper.sendCode(
                    activity = activity,
                    phone = e.phone,
                    resendToken = e.resendToken,
                    onCodeSent = { verificationId, token ->
                        viewModel.onCodeSent(verificationId, token)
                    },
                    onError = {
                        viewModel.onError()
                    }
                )
            }

            is AuthorizationByPhoneEffect.RequestVerifyCode -> {
                phoneAuthHelper.verifyCode(
                    verificationId = e.verificationId,
                    code = e.code,
                    onSuccess = { idToken ->
                        if (idToken != null) {
                            viewModel.onAuthorized(idToken)
                        } else {
                            viewModel.onError()
                        }
                    },
                    onError = {
                        viewModel.onError()
                    }
                )
            }

            is AuthorizationByPhoneEffect.NavigateToRegistration -> {
                onSuccessGetNotRegisterUser(e.userJson)
            }

            is AuthorizationByPhoneEffect.NavigateHome -> {
                onSuccessGetRegisterUser()
            }

            is AuthorizationByPhoneEffect.ShowError -> {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }

            null -> Unit
        }
    }


    Box(modifier = Modifier.fillMaxSize()) {
        AuthorizationByPhoneContent(
            state = state,
            paddingFromSystemUI = paddingFromSystemUi,
            onBackNavigationRequested = onBackNavigationRequested,
            eventCallback = { viewModel.obtainEvent(it) },
        )

        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(1f),
                contentAlignment = Alignment.Center
            ) {
                VolleyballProgressIndicator()
            }
        }
    }
}
