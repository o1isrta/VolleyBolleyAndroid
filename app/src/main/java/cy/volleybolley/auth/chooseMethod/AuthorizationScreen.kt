package cy.volleybolley.auth.chooseMethod

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cy.volleybolley.R
import cy.volleybolley.auth.chooseMethod.model.AuthorizationEffect.NavigateToHome
import cy.volleybolley.auth.chooseMethod.model.AuthorizationEffect.NavigateToRegistration
import cy.volleybolley.auth.chooseMethod.model.AuthorizationEffect.ShowToast
import cy.volleybolley.auth.chooseMethod.model.AuthorizationEvent
import cy.volleybolley.auth.chooseMethod.model.AuthorizationEvent.ContinueWithFacebookClicked
import cy.volleybolley.auth.chooseMethod.model.AuthorizationEvent.GoogleSignInFailed
import cy.volleybolley.auth.chooseMethod.model.AuthorizationEvent.GoogleSignInStarted
import cy.volleybolley.auth.chooseMethod.model.AuthorizationEvent.GoogleTokenReceived
import cy.volleybolley.auth.chooseMethod.model.AuthorizationState
import cy.volleybolley.core.domain.VolleyFeature
import cy.volleybolley.core.presentation.ui.component.ScreenPreviewContainer
import cy.volleybolley.core.presentation.ui.component.VolleyButton
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.util.safeTopPadding
import org.koin.androidx.compose.koinViewModel

@Composable
fun AuthorizationScreen(
    onNavigateToRegisterByPhoneRequested: () -> Unit,
    onNavigateToRegistration: () -> Unit,
    onNavigateToHome: () -> Unit,
    paddingFromSystemUi: PaddingValues,
    viewModel: AuthorizationViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val effect = viewModel.uiEffect.collectAsStateWithLifecycle(null).value
    val context = LocalContext.current

    val onGoogleSignInClick = rememberGoogleSignIn(
        onSignInStarted = { viewModel.obtainEvent(GoogleSignInStarted) },
        onTokenReceived = { viewModel.obtainEvent(GoogleTokenReceived(it)) },
        onSignInFailed = { viewModel.obtainEvent(GoogleSignInFailed) }
    )

    LaunchedEffect(effect) {
        when (effect) {
            is NavigateToRegistration -> onNavigateToRegistration()
            is NavigateToHome -> onNavigateToHome()
            is ShowToast -> Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            null -> { }
        }
    }

    AuthorizationScreen(
        onNavigateToRegisterByPhoneRequested = onNavigateToRegisterByPhoneRequested,
        paddingFromSystemUi = paddingFromSystemUi,
        state = state,
        onGoogleSignInClick = onGoogleSignInClick,
        eventCallback = { viewModel.obtainEvent(it) }
    )
}

@Stable
@Composable
private fun AuthorizationScreen(
    paddingFromSystemUi: PaddingValues,
    state: AuthorizationState,
    onNavigateToRegisterByPhoneRequested: () -> Unit,
    onGoogleSignInClick: () -> Unit,
    eventCallback: (AuthorizationEvent) -> Unit
) {
    Box {
        Image(
            painter = painterResource(id = R.drawable.bg2_launch),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(modifier = Modifier.safeTopPadding(extraTopPadding = 28.dp).fillMaxSize()) {
            VolleyText.TitleXLAlt(
                text = stringResource(id = R.string.sign_up),
                color = VolleyColor.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 21.dp,
                        end = 112.dp
                    ),
                maxLines = 4
            )
            Spacer(modifier = Modifier.weight(1f))
            BottomSheetWithSignButtons(
                modifier = Modifier.fillMaxWidth(),
                paddingFromSystemUi = paddingFromSystemUi,
                isGoogleLoading = state.isGoogleLoading,
                onNavigateToRegisterByPhoneRequested = onNavigateToRegisterByPhoneRequested,
                onGoogleSignInClick = onGoogleSignInClick,
                eventCallback = eventCallback
            )
        }
    }
}

@Stable
@Composable
private fun BottomSheetWithSignButtons(
    modifier: Modifier = Modifier,
    paddingFromSystemUi: PaddingValues,
    isGoogleLoading: Boolean,
    onNavigateToRegisterByPhoneRequested: () -> Unit,
    onGoogleSignInClick: () -> Unit,
    eventCallback: (AuthorizationEvent) -> Unit
) {
    Column(
        modifier = modifier
            .background(
                color = VolleyColor.TurquoiseBottom,
                shape = RoundedCornerShape(
                    topStart = 32.dp,
                    topEnd = 32.dp
                )
            )
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 20.dp,
                bottom = 20.dp + paddingFromSystemUi.calculateBottomPadding()
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (VolleyFeature.IS_AUTH_BY_PHONE_AVAILABLE) {
            Button(
                onClick = onNavigateToRegisterByPhoneRequested,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = VolleyColor.YellowPro),
                shape = RoundedCornerShape(16.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                VolleyText.BodyBoldMedium(
                    text = stringResource(id = R.string.continue_with_phone_number),
                    color = VolleyColor.TextDark
                )
            }
        }
        if (VolleyFeature.IS_AUTH_BY_GOOGLE_AVAILABLE) {
            VolleyButton.ActiveButtonWithLeadingIcon(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                backgroundColor = VolleyColor.White,
                icon = painterResource(R.drawable.ic_google_placeholder),
                text = stringResource(R.string.continue_with_google),
                textColor = VolleyColor.TextDark,
                isLoading = isGoogleLoading,
                onClick = onGoogleSignInClick
            )
        }
        if (VolleyFeature.IS_AUTH_BY_FACEBOOK_AVAILABLE) {
            VolleyButton.ActiveButtonWithLeadingIcon(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                backgroundColor = VolleyColor.BlueLight,
                icon = painterResource(R.drawable.ic_facebook_placeholder),
                text = stringResource(R.string.continue_with_facebook),
                textColor = VolleyColor.White,
                onClick = { eventCallback(ContinueWithFacebookClicked) }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_9_PRO)
@Composable
private fun PreviewAuthorizationScreen() {
    ScreenPreviewContainer {
        AuthorizationScreen(
            onNavigateToRegisterByPhoneRequested = {},
            paddingFromSystemUi = PaddingValues(0.dp),
            state = AuthorizationState(),
            onGoogleSignInClick = {},
            eventCallback = {}
        )
    }
}
