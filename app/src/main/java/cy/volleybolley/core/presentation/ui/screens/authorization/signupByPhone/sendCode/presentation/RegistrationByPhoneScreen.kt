package cy.volleybolley.core.presentation.ui.screens.authorization.signupByPhone.sendCode.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
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
import cy.volleybolley.core.presentation.ui.screens.authorization.signupByPhone.sendCode.presentation.model.SignupByPhoneEffect
import cy.volleybolley.core.presentation.ui.screens.authorization.signupByPhone.sendCode.presentation.model.SignupByPhoneEvent
import cy.volleybolley.core.presentation.ui.screens.authorization.signupByPhone.sendCode.presentation.model.SignupByPhoneState
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegistrationByPhoneScreen(
    viewModel: RegistrationByPhoneViewModel = koinViewModel(),
    paddingFromSystemUi: PaddingValues,
    onBackNavigationRequested: () -> Unit,
    requestNavigateToVerifyPhoneScreen: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val effect by viewModel.uiEffect.collectAsStateWithLifecycle(null)
    LaunchedEffect(effect) {
        when (effect) {
            is SignupByPhoneEffect.NavigateToVerifyPhoneScreen -> {
                requestNavigateToVerifyPhoneScreen()
            }

            null -> {}
        }
    }
    RegistrationByPhoneScreen(
        state = state,
        paddingFromSystemUi = paddingFromSystemUi,
        onBackNavigationRequested = onBackNavigationRequested,
        eventCallback = { viewModel.obtainEvent(it) }
    )
}

@Stable
@Composable
fun RegistrationByPhoneScreen(
    state: SignupByPhoneState,
    paddingFromSystemUi: PaddingValues,
    onBackNavigationRequested: () -> Unit,
    eventCallback: (SignupByPhoneEvent) -> Unit
) {
    VolleyContainersRootTransparent.TransparentContainer(
        modifier = Modifier
            .padding(top = paddingFromSystemUi.calculateTopPadding() + 8.dp, start = 8.dp, end = 8.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(20.dp).fillMaxWidth()) {
            VolleyTopBar.TopBarWithBackButton(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(R.string.registration),
                onBackNavigationRequested = onBackNavigationRequested
            )
            VolleyTextFieldGradient.PhoneTextField(
                modifier = Modifier.padding(top = 16.dp).fillMaxWidth(),
                text = state.phoneNumber,
                alertMessage = if (state.isPhoneNumberInputError) {
                    stringResource(R.string.valid_phone_number)
                } else {
                    ""
                },
                actionToTransferContent = { eventCallback(SignupByPhoneEvent.TypePhoneNumber(it)) }
            )
            VolleyButton.ActiveButton(
                modifier = Modifier.padding(top = 16.dp).fillMaxWidth(),
                enabled = state.isBtnSendCodeEnabled && !state.isLoading,
                text = stringResource(R.string.send_code),
                onClick = { eventCallback(SignupByPhoneEvent.SendCodeButtonClicked) }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewRegistrationByPhoneScreen() {
    RootContainer { paddingFromSystemUi, _ ->
        RegistrationByPhoneScreen(
            state = SignupByPhoneState(
                phoneNumber = "66123",
                isPhoneNumberInputError = true
            ),
            paddingFromSystemUi = paddingFromSystemUi,
            onBackNavigationRequested = {},
            eventCallback = {}
        )
    }
}
