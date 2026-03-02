package cy.volleybolley.auth.ui.screens.authorization

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
import cy.volleybolley.auth.ui.GoogleSignInHelper
import cy.volleybolley.auth.ui.screens.authorization.AuthorizationEffect.LaunchGoogleSignIn
import cy.volleybolley.auth.ui.screens.authorization.AuthorizationEffect.NavigateToHome
import cy.volleybolley.auth.ui.screens.authorization.AuthorizationEffect.NavigateToRegistration
import cy.volleybolley.auth.ui.screens.authorization.AuthorizationEffect.ShowToast
import cy.volleybolley.auth.ui.screens.authorization.AuthorizationEvent.ContinueWithFacebookClicked
import cy.volleybolley.auth.ui.screens.authorization.AuthorizationEvent.ContinueWithGoogleClicked
import cy.volleybolley.auth.ui.screens.authorization.AuthorizationEvent.GoogleSignInCancelled
import cy.volleybolley.auth.ui.screens.authorization.AuthorizationEvent.GoogleSignInFailed
import cy.volleybolley.auth.ui.screens.authorization.AuthorizationEvent.GoogleTokenReceived
import cy.volleybolley.core.domain.VolleyFeature
import cy.volleybolley.core.presentation.ui.component.ScreenPreviewContainer
import cy.volleybolley.core.presentation.ui.component.VolleyButton
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.model.VolleyUiUtil.showDebugLog
import org.koin.androidx.compose.koinViewModel

@Composable
fun AuthorizationScreen(
    onNavigateToRegisterByPhoneRequested: () -> Unit,
    onSuccessGetNotRegisterUser: (String) -> Unit,
    onSuccessGetRegisterUser: () -> Unit,
    paddingFromSystemUi: PaddingValues,
    viewModel: AuthorizationViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val effect by viewModel.uiEffect.collectAsStateWithLifecycle(null)
    val screenTag: String = stringResource(R.string.auth_screen_log_tag)
    val errorTitle: String = stringResource(R.string.auth_error_no_google_acc_on_device)

    val context = LocalContext.current
    val googleSignInHelper = GoogleSignInHelper(context)

    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        when (result.resultCode) {
            Activity.RESULT_OK -> {
                val googleIdToken = googleSignInHelper.extractGoogleIdToken(result.data)
                viewModel.obtainEvent(GoogleTokenReceived(googleIdToken))
            }

            Activity.RESULT_CANCELED -> {
                viewModel.obtainEvent(GoogleSignInCancelled)
            }

            else -> {
                viewModel.obtainEvent(GoogleSignInFailed)
            }
        }
    }

    LaunchedEffect(effect) {
        showDebugLog(screenTag, "LaunchedEffect triggered: $effect")

        when (effect) {
            is LaunchGoogleSignIn -> {
                showDebugLog(screenTag, "🚀 Starting Google Sign-In flow")
                val intentSender = googleSignInHelper.signIn()

                showDebugLog(screenTag, "IntentSender: $intentSender")

                if (intentSender != null) {
                    showDebugLog(screenTag, "✅ Launching intent...")
                    googleSignInLauncher.launch(IntentSenderRequest.Builder(intentSender).build())
                } else {
                    showDebugLog(screenTag, "❌ IntentSender is null!")
                    Toast.makeText(context, errorTitle, Toast.LENGTH_SHORT).show()
                }

                // Reset the effect after processing so that it can be started again
                viewModel.absorbEffect()
            }

            is NavigateToRegistration -> {
                showDebugLog(screenTag, "Navigate to registration")
                val user = (effect as NavigateToRegistration).user
                onSuccessGetNotRegisterUser(user)
            }

            is NavigateToHome -> {
                onSuccessGetRegisterUser()
            }

            is ShowToast -> {
                Toast.makeText(context, (effect as ShowToast).message, Toast.LENGTH_SHORT).show()
            }

            null -> {
                showDebugLog(screenTag, "Effect is null")
            }
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
                onClick = { eventCallback(ContinueWithGoogleClicked) }
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
                onClick = { eventCallback(ContinueWithFacebookClicked) }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewAuthorizationScreen() {
    ScreenPreviewContainer {
        AuthorizationScreen(
            onNavigateToRegisterByPhoneRequested = {},
            paddingFromSystemUi = PaddingValues(0.dp),
            state = AuthorizationState(),
            eventCallback = {}
        )
    }
}
