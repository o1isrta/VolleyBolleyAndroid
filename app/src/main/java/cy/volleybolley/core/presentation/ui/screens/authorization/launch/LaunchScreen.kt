package cy.volleybolley.core.presentation.ui.screens.authorization.launch

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.component.VolleyProgress
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.navigation.AuthorizationRoute
import cy.volleybolley.core.presentation.ui.navigation.HomeTopLevelRoute
import cy.volleybolley.core.presentation.ui.navigation.LaunchRoute
import cy.volleybolley.core.presentation.ui.navigation.OnboardingRoute
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LaunchScreen(
    navController: NavHostController,
    viewModel: LaunchViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val effect by viewModel.uiEffect.collectAsStateWithLifecycle(null)
    LaunchedEffect(effect) {
        when (effect) {
            is LaunchScreenEffect.NavigateToOnboarding -> {
                navController.navigate(OnboardingRoute) {
                    popUpTo(LaunchRoute) { inclusive = true }
                }
            }

            is LaunchScreenEffect.NavigateToHome -> {
                navController.navigate(HomeTopLevelRoute) {
                    popUpTo(LaunchRoute) { inclusive = true }
                }
            }

            is LaunchScreenEffect.NavigateToAuthorization -> {
                navController.navigate(AuthorizationRoute) {
                    popUpTo(LaunchRoute) { inclusive = true }
                }
            }

            null -> {}
        }
    }
    LaunchScreen(state = state)
}

@Composable
fun LaunchScreen(state: LaunchScreenState) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VolleyColor.TurquoiseDark)
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg_launch),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        LogoWithAppName(
            modifier = Modifier.align(Alignment.Center),
            isProgressBarVisible = state.isLoading
        )
    }
}

@Stable
@Composable
fun LogoWithAppName(modifier: Modifier = Modifier, isProgressBarVisible: Boolean = false) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = null,
            modifier = Modifier.size(VolleyDimens.LAUNCH_LOGO_SIZE.dp)
        )
        VolleyText.LogoDisplay(
            modifier = Modifier.padding(top = 24.dp),
            text = stringResource(id = R.string.volleybolley),
            color = VolleyColor.White
        )
        if (isProgressBarVisible) {
            VolleyProgress.CircularProgress(modifier = Modifier.padding(top = 16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LaunchScreenPreview() {
    LaunchScreen(state = LaunchScreenState(isLoading = true))
}
