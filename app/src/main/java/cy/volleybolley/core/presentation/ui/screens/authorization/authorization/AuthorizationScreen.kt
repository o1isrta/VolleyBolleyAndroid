package cy.volleybolley.core.presentation.ui.screens.authorization.authorization

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cy.volleybolley.R
import cy.volleybolley.auth.data.UserDto
import cy.volleybolley.core.domain.VolleyFeature
import cy.volleybolley.core.presentation.RootContainer
import cy.volleybolley.core.presentation.ui.component.VolleyButton
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText
import org.koin.androidx.compose.koinViewModel

@Composable
fun AuthorizationScreen(
    onNavigateToRegisterByPhoneRequested: () -> Unit,
    onSuccessRegisteredAction: (String) -> Unit,
    paddingFromSystemUi: PaddingValues,
    viewModel: AuthorizationViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val effect by viewModel.uiEffect.collectAsStateWithLifecycle(null)

    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        val idToken = if (result.resultCode == Activity.RESULT_OK) {
            viewModel.extractGoogleIdToken(result.data)
        } else null

        viewModel.obtainEvent(AuthorizationEvent.GoogleTokenReceived(idToken))
    }

    LaunchedEffect(effect) {
        when (effect) {
            is AuthorizationEffect.LaunchGoogleSignIn -> {
                val intentSender = (effect as AuthorizationEffect.LaunchGoogleSignIn).intentSender
                launcher.launch(
                    IntentSenderRequest.Builder(intentSender).build()
                )
            }

            is AuthorizationEffect.NavigateToRegistration -> {
                val user = (effect as AuthorizationEffect.NavigateToRegistration).user
                onSuccessRegisteredAction(user)
            }
            is AuthorizationEffect.ShowError -> {
                val message = (effect as AuthorizationEffect.ShowError).message
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }

            null -> {}
        }
    }
    AuthorizationScreen(
        onNavigateToRegisterByPhoneRequested = onNavigateToRegisterByPhoneRequested,
        paddingFromSystemUi = paddingFromSystemUi,
        state = state,
        eventCallback = { viewModel.obtainEvent(it) }
    )
}

@Composable
fun AuthorizationScreen(
    paddingFromSystemUi: PaddingValues,
    state: AuthorizationState,
    onNavigateToRegisterByPhoneRequested: () -> Unit,
    eventCallback: (AuthorizationEvent) -> Unit
) {
    Box {
        Image(
            painter = painterResource(id = R.drawable.bg2_launch),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .padding(top = paddingFromSystemUi.calculateTopPadding() + 28.dp)
                .fillMaxSize()
        ) {
            VolleyText.TitleXLAlt(
                text = stringResource(id = R.string.sign_up),
                color = VolleyColor.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 21.dp,
                        end = VolleyDimens.DIMEN_112.dp
                    ),
                maxLines = 4
            )
            Spacer(modifier = Modifier.weight(1f))
            if (state.isLoading.not()) {
                BottomSheetWithSignButtons(
                    modifier = Modifier.fillMaxWidth(),
                    paddingFromSystemUi = paddingFromSystemUi,
                    onNavigateToRegisterByPhoneRequested = onNavigateToRegisterByPhoneRequested,
                    eventCallback = eventCallback
                )
            }
        }
    }
}

@Stable
@Composable
private fun BottomSheetWithSignButtons(
    modifier: Modifier = Modifier,
    paddingFromSystemUi: PaddingValues,
    onNavigateToRegisterByPhoneRequested: () -> Unit,
    eventCallback: (AuthorizationEvent) -> Unit
) {
    Column(
        modifier = modifier
            .background(
                color = VolleyColor.TurquoiseBottom,
                shape = RoundedCornerShape(
                    topStart = VolleyDimens.DIMEN_32.dp,
                    topEnd = VolleyDimens.DIMEN_32.dp
                )
            )
            .padding(
                start = VolleyDimens.DIMEN_16.dp,
                end = VolleyDimens.DIMEN_16.dp,
                top = VolleyDimens.DIMEN_20.dp,
                bottom = VolleyDimens.DIMEN_20.dp + paddingFromSystemUi.calculateBottomPadding()
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        @Suppress("KotlinConstantConditions")
        if (VolleyFeature.IS_AUTH_BY_PHONE_AVAILABLE) {
            Button(
                onClick = onNavigateToRegisterByPhoneRequested,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(VolleyDimens.DIMEN_56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = VolleyColor.YellowPro),
                shape = RoundedCornerShape(VolleyDimens.DIMEN_16.dp),
                contentPadding = PaddingValues(horizontal = VolleyDimens.DIMEN_16.dp)
            ) {
                VolleyText.BodyBoldMedium(
                    text = stringResource(id = R.string.continue_with_phone_number),
                    color = VolleyColor.TextDark
                )
            }
        }
        @Suppress("KotlinConstantConditions")
        if (VolleyFeature.IS_AUTH_BY_GOOGLE_AVAILABLE) {
            VolleyButton.ActiveButtonWithLeadingIcon(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                backgroundColor = VolleyColor.White,
                icon = painterResource(R.drawable.ic_google_placeholder),
                text = stringResource(R.string.continue_with_google),
                textColor = VolleyColor.TextDark,
                onClick = { eventCallback(AuthorizationEvent.ContinueWithGoogleClicked) }
            )
        }
        @Suppress("KotlinConstantConditions")
        if (VolleyFeature.IS_AUTH_BY_FACEBOOK_AVAILABLE) {
            VolleyButton.ActiveButtonWithLeadingIcon(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                backgroundColor = VolleyColor.BlueLight,
                icon = painterResource(R.drawable.ic_facebook_placeholder),
                text = stringResource(R.string.continue_with_facebook),
                textColor = VolleyColor.White,
                onClick = { eventCallback(AuthorizationEvent.ContinueWithFacebookClicked) }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewAuthorizationScreen() {
    RootContainer { paddingFromSystemUi, navController ->
        AuthorizationScreen(
            onNavigateToRegisterByPhoneRequested = {},
            paddingFromSystemUi = paddingFromSystemUi,
            state = AuthorizationState(),
            eventCallback = {}
        )
    }
}
