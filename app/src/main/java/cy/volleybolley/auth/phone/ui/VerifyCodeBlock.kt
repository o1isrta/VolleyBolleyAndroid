package cy.volleybolley.auth.phone.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cy.volleybolley.R
import cy.volleybolley.auth.phone.ui.presentation.model.AuthorizationByPhoneEvent
import cy.volleybolley.auth.phone.ui.presentation.model.AuthorizationByPhoneState
import cy.volleybolley.core.presentation.ui.VolleyTextFieldGradient
import cy.volleybolley.core.presentation.ui.component.VolleyButton

@Composable
fun VerifyCodeBlock(
    state: AuthorizationByPhoneState,
    eventCallback: (AuthorizationByPhoneEvent) -> Unit
) {
    var codeInput by rememberSaveable { mutableStateOf(state.code) }

    LaunchedEffect(state.code) {
        if (state.code != codeInput) {
            codeInput = state.code
        }
    }

    VolleyTextFieldGradient.PhoneCodeTextField(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth(),
        text = codeInput,
        alertMessage = if (state.isCodeInputError) {
            stringResource(R.string.invalid_code)
        } else {
            ""
        },
        actionToTransferContent = {
            codeInput = it
            eventCallback(AuthorizationByPhoneEvent.TypeCode(it))
        }
    )

    AnimatedVisibility(
        visible = state.isResendVisible
    ) {
        Box(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            VolleyButton.GradientTextButton(
                text = if (state.isResendEnabled) {
                    stringResource(R.string.get_new_code)
                } else {
                    stringResource(R.string.resend_in, formatSeconds(state.remainingResendTime))
                },
                isEnable = state.isResendEnabled && !state.isLoading,
                onClick = {
                    eventCallback(AuthorizationByPhoneEvent.ResendCodeClicked)
                }
            )
        }
    }

    VolleyButton.ActiveButton(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth(),
        enabled = state.code.length >= 6 && !state.isLoading,
        text = stringResource(R.string.verify),
        onClick = {
            eventCallback(AuthorizationByPhoneEvent.VerifyCodeClicked)
        }
    )
}

private const val SIXTY = 60

private fun formatSeconds(seconds: Int): String {
    val minutes = seconds / SIXTY
    val remainingSeconds = seconds % SIXTY
    return "%02d:%02d".format(minutes, remainingSeconds)
}
