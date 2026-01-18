package cy.volleybolley.core.presentation.ui.screens.authorization.authorizationByPhone.sendCode.presentation

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
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.VolleyTextFieldGradient
import cy.volleybolley.core.presentation.ui.component.VolleyButton
import cy.volleybolley.core.presentation.ui.component.VolleyTopBar
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.screens.authorization.authorizationByPhone.sendCode.presentation.model.AuthorizationByPhoneEffect
import cy.volleybolley.core.presentation.ui.screens.authorization.authorizationByPhone.sendCode.presentation.model.AuthorizationByPhoneEvent
import cy.volleybolley.core.presentation.ui.screens.authorization.authorizationByPhone.sendCode.presentation.model.AuthorizationByPhoneState
import org.koin.androidx.compose.koinViewModel

@Composable
fun AuthorizationByPhoneScreen(
    viewModel: AuthorizationByPhoneViewModel = koinViewModel(),
    paddingFromSystemUi: PaddingValues,
    onBackNavigationRequested: () -> Unit,
    requestNavigateToVerifyPhoneScreen: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val effect by viewModel.uiEffect.collectAsStateWithLifecycle(null)
    LaunchedEffect(effect) {
        when (effect) {
            is AuthorizationByPhoneEffect.NavigateToVerifyPhoneScreen -> {
                requestNavigateToVerifyPhoneScreen()
            }

            null -> {}
        }
    }
    AuthorizationByPhoneScreen(
        state = state,
        paddingFromSystemUi = paddingFromSystemUi,
        onBackNavigationRequested = onBackNavigationRequested,
        eventCallback = { viewModel.obtainEvent(it) }
    )
}

@Stable
@Composable
fun AuthorizationByPhoneScreen(
    state: AuthorizationByPhoneState,
    paddingFromSystemUi: PaddingValues,
    onBackNavigationRequested: () -> Unit,
    eventCallback: (AuthorizationByPhoneEvent) -> Unit
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
            VolleyTextFieldGradient.PhoneTextField(
                modifier = Modifier
                    .padding(top = VolleyDimens.DIMEN_16.dp)
                    .fillMaxWidth(),
                text = state.phoneNumber,
                alertMessage = if (state.isPhoneNumberInputError) {
                    stringResource(R.string.valid_phone_number)
                } else {
                    ""
                },
                actionToTransferContent = { eventCallback(AuthorizationByPhoneEvent.TypePhoneNumber(it)) }
            )
            VolleyButton.ActiveButton(
                modifier = Modifier
                    .padding(top = VolleyDimens.DIMEN_16.dp)
                    .fillMaxWidth(),
                enabled = state.isBtnSendCodeEnabled && !state.isLoading,
                text = stringResource(R.string.send_code),
                onClick = { eventCallback(AuthorizationByPhoneEvent.SendCodeButtonClicked) }
            )
        }
    }
}

@Composable
fun PreviewRootContainer(content: @Composable (PaddingValues) -> Unit) {
    val fakePadding = PaddingValues(0.dp) // Или любые другие значения
    VolleyContainersRootTransparent.TransparentContainer {
        content(fakePadding)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewAuthorizationByPhoneScreen() {
    PreviewRootContainer { paddingFromSystemUi -> // Use PreviewRootContainer here
        AuthorizationByPhoneScreen(
            state = AuthorizationByPhoneState(
                phoneNumber = stringResource(R.string.default_phone_number),
                isPhoneNumberInputError = true
            ),
            paddingFromSystemUi = paddingFromSystemUi,
            onBackNavigationRequested = {},
            eventCallback = {}
        )
    }
}
