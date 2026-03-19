package cy.volleybolley.core.presentation.ui.screens.findatourney

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
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.VolleySimpleComponent.TitleWithBackArrow
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyText
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun IndividualPlayersScreen(
    onNavigateBack: () -> Unit,
    viewModel: IndividualPlayersScreenViewModel = koinViewModel(),
    paddingFromSystemUi: PaddingValues
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val effect by viewModel.uiEffect.collectAsStateWithLifecycle(null)

    LaunchedEffect(effect) {
        when (effect) {
            is IndividualPlayersScreenEffect.NavigateBack -> onNavigateBack()
            null -> {}
        }
    }

    IndividualPlayersScreen(
        state = state,
        paddingFromSystemUi = paddingFromSystemUi,
        eventCallback = { viewModel.obtainEvent(it) }
    )
}

@Stable
@Composable
private fun IndividualPlayersScreen(
    state: IndividualPlayersScreenState,
    paddingFromSystemUi: PaddingValues,
    eventCallback: (IndividualPlayersScreenEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingFromSystemUi)
    ) {
        VolleyContainersRootTransparent.TransparentContainer(
            modifier = Modifier.padding(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                TitleWithBackArrow(
                    title = stringResource(R.string.players),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 20.dp),
                    onBackClick = { eventCallback(IndividualPlayersScreenEvent.OnBackClicked) }
                )

                VolleyText.BodyRegular(
                    text = "Individual players screen - TODO",
                    modifier = Modifier.padding(top = 20.dp),
                    color = VolleyColor.White
                )
            }
        }
    }
}

@Preview
@Composable
private fun IndividualPlayersScreenPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VolleyColor.TurquoiseDark)
    ) {
        IndividualPlayersScreen(
            state = IndividualPlayersScreenState(),
            paddingFromSystemUi = PaddingValues(0.dp),
            eventCallback = {}
        )
    }
}
