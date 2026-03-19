package cy.volleybolley.core.presentation.ui.screens.authorization.launch

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.component.VolleyButton
import cy.volleybolley.core.presentation.ui.component.VolleyProgress
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyText
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LaunchScreen(
    paddingFromSystemUi: PaddingValues,
    onNavigateToOnboarding: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToAuthorization: () -> Unit,
    viewModel: LaunchViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val effect by viewModel.uiEffect.collectAsStateWithLifecycle(null)

    LaunchedEffect(effect) {
        when (effect) {
            is LaunchScreenEffect.NavigateToOnboarding -> onNavigateToOnboarding()
            is LaunchScreenEffect.NavigateToHome -> onNavigateToHome()
            is LaunchScreenEffect.NavigateToAuthorization -> onNavigateToAuthorization()
            null -> {}
        }
    }

    LaunchScreen(
        state = state,
        paddingFromSystemUi = paddingFromSystemUi,
        eventCallback = { viewModel.obtainEvent(it) }
    )
}

@Stable
@Composable
private fun LaunchScreen(
    state: LaunchScreenState,
    paddingFromSystemUi: PaddingValues,
    eventCallback: (LaunchScreenEvent) -> Unit = {}
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.padding(paddingFromSystemUi)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(VolleyColor.TurquoiseDark)
        ) {
            Image(
                painter = painterResource(id = R.drawable.bg_launch),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            LogoWithAppName(
                onBlockSizeChanged = {}
            )
        }

        Column {
            Spacer(Modifier.weight(1f))
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier.weight(1f)
            ) {
                when (state) {
                    LaunchScreenState.Loading -> {
                        VolleyProgress.CircularProgress(
                            modifier = Modifier
                                .padding(bottom = 52.dp)
                        )
                    }

                    is LaunchScreenState.Error -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.iv_error),
                                contentDescription = null,
                                modifier = Modifier.size(120.dp)
                            )
                            VolleyText.TitleMedium(
                                text = stringResource(state.errorMessageRes),
                                color = VolleyColor.White
                            )
                            Spacer(Modifier.height(16.dp))
                            VolleyButton.ActiveButton(
                                text = stringResource(R.string.retry),
                                onClick = { eventCallback(LaunchScreenEvent.RetryClicked) },
                                modifier = Modifier.padding(bottom = 52.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Stable
@Composable
fun LogoWithAppName(
    modifier: Modifier = Modifier,
    onBlockSizeChanged: (IntSize) -> Unit,
) {
    Column(
        modifier = modifier
            .onSizeChanged { size ->
                onBlockSizeChanged(size)
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = null,
            modifier = Modifier.size(200.dp)
        )
        VolleyText.LogoDisplay(
            modifier = Modifier.padding(top = 24.dp),
            text = stringResource(id = R.string.volleybolley),
            color = VolleyColor.White
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LaunchScreenLoadingPreview() {
    LaunchScreen(
        state = LaunchScreenState.Loading,
        paddingFromSystemUi = PaddingValues(bottom = 40.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun LaunchScreenErrorPreview() {
    LaunchScreen(
        state = LaunchScreenState.Error(R.string.something_went_wrong),
        paddingFromSystemUi = PaddingValues(bottom = 40.dp)
    )
}
