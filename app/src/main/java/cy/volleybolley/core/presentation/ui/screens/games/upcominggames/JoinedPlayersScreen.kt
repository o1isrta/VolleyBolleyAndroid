package cy.volleybolley.core.presentation.ui.screens.games.upcominggames

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.component.VolleyTopBar.TopBarWithBackButton
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyMocks
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.games.domain.model.entity.PlayerShort
import cy.volleybolley.games.domain.model.event.tournament.TournamentDetails
import cy.volleybolley.ui.theme.VolleybolleyTheme
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun JoinedPlayersScreen(
    paddingFromSystemUi: PaddingValues,
    tournamentDetails: TournamentDetails?,
    onNavigateBack: () -> Unit,
    viewModel: JoinedPlayersScreenViewModel = koinViewModel { parametersOf(tournamentDetails) }
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val effect by viewModel.uiEffect.collectAsStateWithLifecycle(null)

    LaunchedEffect(effect) {
        when (effect) {
            is JoinedPlayersScreenEffect.NavigateBack -> onNavigateBack()
            null -> {}
        }
    }

    JoinedPlayersScreen(
        state = state,
        paddingFromSystemUi = paddingFromSystemUi,
        eventCallback = { viewModel.obtainEvent(it) }
    )
}

@Stable
@Composable
private fun JoinedPlayersScreen(
    state: JoinedPlayersScreenState,
    paddingFromSystemUi: PaddingValues,
    eventCallback: (JoinedPlayersScreenEvent) -> Unit
) {
    val tournamentDetails = state.tournamentDetails ?: return

    Box(
        modifier = Modifier
            .padding(paddingFromSystemUi)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        VolleyContainersRootTransparent.TransparentContainer(
            cornerRadius = 32,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(20.dp)
            ) {
                TopBarWithBackButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    title = stringResource(R.string.players),
                    onBackNavigationRequested = { eventCallback(JoinedPlayersScreenEvent.OnBackClicked) }
                )

                val players = tournamentDetails.teams.firstOrNull()?.players ?: emptyList()
                val currentPlayersNumber = players.size

                for (i in 1..tournamentDetails.maximumPlayers) {
                    if (i <= currentPlayersNumber) {
                        PlayersRow(i, players[i - 1])
                    } else {
                        PlayersRow(i)
                    }
                }
            }
        }
    }
}

@Stable
@Composable
private fun PlayersRow(index: Int, player: PlayerShort) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        VolleyText.BodyRegular(
            text = "$index. ${player.name}",
            color = VolleyColor.White,
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        )

        Box(
            modifier = Modifier
                .size(32.dp, 20.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(VolleyColor.GreyDark)
        ) {
            VolleyText.BodyRegular(
                text = player.level.name.first().toString(),
                color = VolleyColor.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Stable
@Composable
private fun PlayersRow(index: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        VolleyText.BodyRegular(
            text = "$index. ${stringResource(R.string.free_spot)}",
            color = VolleyColor.White,
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        )
    }
}

@Composable
@Preview(showSystemUi = true)
private fun JoinedPlayersScreenPreview() {
    VolleybolleyTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(VolleyColor.TurquoiseDark)
        ) {
            JoinedPlayersScreen(
                state = JoinedPlayersScreenState(tournamentDetails = VolleyMocks.mockTournament),
                paddingFromSystemUi = PaddingValues(0.dp),
                eventCallback = {}
            )
        }
    }
}
