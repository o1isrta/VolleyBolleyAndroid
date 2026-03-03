package cy.volleybolley.core.presentation.ui.screens.games.gameinvites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.VolleySimpleComponent.TitleWithBackArrow
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyText
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun JoinTheTourneyScreen(
    navController: NavHostController,
    viewModel: JoinTheTourneyScreenViewModel = koinViewModel(),
    paddingFromSystemUi: PaddingValues
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val effect by viewModel.uiEffect.collectAsStateWithLifecycle(null)

    LaunchedEffect(effect) {
        when (effect) {
            is JoinTheTourneyScreenEffect.NavigateBack -> navController.popBackStack()
            null -> {}
        }
    }

    JoinTheTourneyScreen(
        state = state,
        paddingFromSystemUi = paddingFromSystemUi,
        eventCallback = { viewModel.obtainEvent(it) }
    )
}

@Stable
@Composable
private fun JoinTheTourneyScreen(
    state: JoinTheTourneyScreenState,
    paddingFromSystemUi: PaddingValues,
    eventCallback: (JoinTheTourneyScreenEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingFromSystemUi)
    ) {
        VolleyContainersRootTransparent.TransparentContainer(
            cornerRadius = 32,
            modifier = Modifier.padding(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                TitleWithBackArrow(
                    title = stringResource(R.string.join_the_tourney),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 20.dp),
                    onBackClick = { eventCallback(JoinTheTourneyScreenEvent.OnBackClicked) }
                )

                VolleyText.BodyRegular(
                    text = "Join the tourney screen - TODO",
                    modifier = Modifier.padding(top = 20.dp),
                    color = VolleyColor.White
                )
            }
        }
    }
}

@Preview
@Composable
private fun JoinTheTourneyScreenPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VolleyColor.TurquoiseDark)
    ) {
        JoinTheTourneyScreen(
            state = JoinTheTourneyScreenState(),
            paddingFromSystemUi = PaddingValues(0.dp),
            eventCallback = {}
        )
    }
}
