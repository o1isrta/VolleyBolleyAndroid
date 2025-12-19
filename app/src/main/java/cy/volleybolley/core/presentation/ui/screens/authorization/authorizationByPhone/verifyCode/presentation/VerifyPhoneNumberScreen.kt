package cy.volleybolley.core.presentation.ui.screens.authorization.authorizationByPhone.verifyCode.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cy.volleybolley.R
import cy.volleybolley.core.presentation.RootContainer
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.VolleyTextFieldGradient
import cy.volleybolley.core.presentation.ui.component.VolleyButton
import cy.volleybolley.core.presentation.ui.component.VolleyTopBar
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.screens.authorization.authorizationByPhone.verifyCode.presentation.model.VerifyPhoneNumberEffect
import cy.volleybolley.core.presentation.ui.screens.authorization.authorizationByPhone.verifyCode.presentation.model.VerifyPhoneNumberEvent
import cy.volleybolley.core.presentation.ui.screens.authorization.authorizationByPhone.verifyCode.presentation.model.VerifyPhoneNumberState
import org.koin.androidx.compose.koinViewModel

@Composable
fun VerifyPhoneNumberScreen(
    viewModel: VerifyPhoneNumberViewModel = koinViewModel(),
    paddingFromSystemUi: PaddingValues,
    onBackNavigationRequested: () -> Unit,
    onNavigateToRegistrationScreenRequested: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val effect by viewModel.uiEffect.collectAsStateWithLifecycle(null)
    LaunchedEffect(effect) {
        when (effect) {
            is VerifyPhoneNumberEffect.NavigateToRegistrationScreen -> onNavigateToRegistrationScreenRequested()
            null -> {}
        }
    }
    VerifyPhoneNumberScreen(
        state = state,
        paddingFromSystemUi = paddingFromSystemUi,
        onBackNavigationRequested = onBackNavigationRequested,
        eventCallback = { viewModel.obtainEvent(it) }
    )
}

@Stable
@Composable
fun VerifyPhoneNumberScreen(
    state: VerifyPhoneNumberState,
    paddingFromSystemUi: PaddingValues,
    onBackNavigationRequested: () -> Unit,
    eventCallback: (VerifyPhoneNumberEvent) -> Unit
) {
    VolleyContainersRootTransparent.TransparentContainer(
        modifier = Modifier
            .padding(
                top = paddingFromSystemUi.calculateTopPadding() + VolleyDimens.DIMEN_8.dp,
                start = VolleyDimens.DIMEN_8.dp,
                end = VolleyDimens.DIMEN_8.dp
            )
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(VolleyDimens.DIMEN_20.dp)
                .fillMaxWidth()
        ) {
            VolleyTopBar.TopBarWithBackButton(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(R.string.registration),
                onBackNavigationRequested = onBackNavigationRequested
            )
            VolleyTextFieldGradient.PhoneCodeTextField(
                modifier = Modifier
                    .padding(top = VolleyDimens.DIMEN_16.dp)
                    .fillMaxWidth(),
                text = state.code,
                alertMessage = if (state.isCodeInputError) {
                    stringResource(R.string.invalid_code)
                } else {
                    ""
                },
                actionToTransferContent = { eventCallback(VerifyPhoneNumberEvent.TypeCode(it)) }
            )
            AnimatedVisibility(
                modifier = Modifier
                    .padding(top = VolleyDimens.DIMEN_8.dp)
                    .align(Alignment.CenterHorizontally),
                visible = state.isBtnNewCodeVisible
            ) {
                VolleyButton.GradientTextButton(
                    text = if (state.isBtnNewCodeEnabled) {
                        stringResource(R.string.get_new_code)
                    } else {
                        stringResource(R.string.resend_in, state.remainingSendNewCodeTime)
                    },
                    isEnable = state.isBtnNewCodeEnabled && !state.isLoading,
                    onClick = { eventCallback(VerifyPhoneNumberEvent.SendNewCodeButtonClicked) }
                )
            }
            VolleyButton.ActiveButton(
                modifier = Modifier
                    .padding(top = VolleyDimens.DIMEN_16.dp)
                    .fillMaxWidth(),
                enabled = state.isBtnVerifyEnabled && !state.isLoading,
                text = stringResource(R.string.send_code),
                onClick = { eventCallback(VerifyPhoneNumberEvent.VerifyCodeButtonClicked) }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewVerifyPhoneNumberScreen() {
    RootContainer { paddingFromSystemUi, _ ->
        VerifyPhoneNumberScreen(
            state = VerifyPhoneNumberState(
                isLoading = false,
                code = "777777",
                isBtnVerifyEnabled = false,
                isCodeInputError = true,
                isBtnNewCodeVisible = true,
                isBtnNewCodeEnabled = false,
                remainingSendNewCodeTime = "00:29"
            ),
            paddingFromSystemUi = paddingFromSystemUi,
            onBackNavigationRequested = {},
            eventCallback = {}
        )
    }
}
