package cy.volleybolley.core.presentation.ui.screens.findatourney

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
import cy.volleybolley.core.presentation.RootContainerForPreview
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.VolleySimpleComponent.TitleWithBackArrow
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyText
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun InvitePlayersScreen(
    onNavigateBack: () -> Unit,
    viewModel: InvitePlayersViewModel = koinViewModel(),
    paddingFromSystemUi: PaddingValues
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val effect by viewModel.uiEffect.collectAsStateWithLifecycle(null)

    LaunchedEffect(effect) {
        when (effect) {
            is InvitePlayersEffect.NavigateBack -> onNavigateBack()
            null -> {}
        }
    }

    InvitePlayersScreen(
        state = state,
        paddingFromSystemUi = paddingFromSystemUi,
        eventCallback = { viewModel.obtainEvent(it) }
    )
}

@Stable
@Composable
private fun InvitePlayersScreen(
    state: InvitePlayersState,
    paddingFromSystemUi: PaddingValues,
    eventCallback: (InvitePlayersEvent) -> Unit
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
                    title = stringResource(R.string.invite_players),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 20.dp),
                    onBackClick = { eventCallback(InvitePlayersEvent.OnBackClicked) }
                )

                VolleyText.BodyRegular(
                    text = "Invite players screen - TODO",
                    modifier = Modifier.padding(top = 20.dp),
                    color = VolleyColor.White
                )
            }
        }
    }
}

@Preview
@Composable
private fun InvitePlayersScreenPreview() {
    RootContainerForPreview {
        InvitePlayersScreen(
            state = InvitePlayersState(),
            paddingFromSystemUi = PaddingValues(0.dp),
            eventCallback = {}
        )
    }
}
