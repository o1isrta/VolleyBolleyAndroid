package cy.volleybolley.core.presentation.ui.screens.games.archive.teamsscreen

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
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cy.volleybolley.R
import cy.volleybolley.core.presentation.RootContainerForPreview
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.component.VolleyTopBar.TopBarWithBackButton
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.screens.games.archive.util.provideMockTeams
import cy.volleybolley.core.presentation.ui.screens.games.archive.teamsscreen.effect.TeamsScreenEffect
import cy.volleybolley.core.presentation.ui.screens.games.archive.teamsscreen.event.TeamsScreenEvent
import cy.volleybolley.core.presentation.ui.screens.games.archive.teamsscreen.model.TeamsScreenState
import cy.volleybolley.core.presentation.ui.screens.games.archive.teamsscreen.viewmodel.TeamsScreenViewModel
import cy.volleybolley.games.domain.model.entity.PlayerShort
import cy.volleybolley.games.domain.model.entity.Team
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TeamsScreen(
    paddingFromSystemUi: PaddingValues,
    onNavigateBack: () -> Unit,
    viewModel: TeamsScreenViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val effect by viewModel.uiEffect.collectAsStateWithLifecycle(null)

    LaunchedEffect(effect) {
        when (effect) {
            is TeamsScreenEffect.NavigateBack -> onNavigateBack()
            null -> {}
        }
    }

    TeamsScreen(
        state = state,
        paddingFromSystemUi = paddingFromSystemUi,
        eventCallback = { viewModel.obtainEvent(it) }
    )
}

@Stable
@Composable
private fun TeamsScreen(
    state: TeamsScreenState,
    paddingFromSystemUi: PaddingValues,
    eventCallback: (TeamsScreenEvent) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingFromSystemUi)
            .verticalScroll(rememberScrollState())
    ) {
        VolleyContainersRootTransparent.TransparentContainer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                if (state.isIndividual) {
                    PlayersList(
                        modifier = Modifier.fillMaxWidth(),
                        teams = state.teams,
                        onBackClick = { eventCallback(TeamsScreenEvent.OnBackClicked) }
                    )
                } else {
                    TeamsList(
                        modifier = Modifier.fillMaxWidth(),
                        teams = state.teams,
                        onBackClick = { eventCallback(TeamsScreenEvent.OnBackClicked) }
                    )
                }
            }
        }
    }
}

@Stable
@Composable
private fun PlayersList(
    modifier: Modifier = Modifier,
    teams: List<Team>,
    onBackClick: () -> Unit
) {
    val players = teams.firstOrNull()?.players ?: emptyList()

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        TopBarWithBackButton(
            modifier = modifier,
            title = stringResource(R.string.players),
            onBackNavigationRequested = onBackClick
        )

        players.forEachIndexed { index, player ->
            PlayersRow(index, player)
        }
    }
}

@Stable
@Composable
private fun PlayersRow(index: Int, player: PlayerShort) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        VolleyText.BodyRegular(
            text = "${index + 1}. ${player.name}",
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
private fun TeamsList(
    modifier: Modifier = Modifier,
    teams: List<Team>,
    onBackClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        TopBarWithBackButton(
            modifier = modifier,
            title = stringResource(R.string.teams),
            onBackNavigationRequested = onBackClick
        )

        teams.forEachIndexed { index, team ->
            TeamBlock(
                team = team,
                teamIndex = index
            )
        }
    }
}

@Stable
@Composable
private fun TeamBlock(
    modifier: Modifier = Modifier,
    team: Team,
    teamIndex: Int
) {
    val topPadding = if (teamIndex == 0) 16.dp else 20.dp

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = topPadding)
    ) {
        VolleyText.TitleMedium(
            text = stringResource(R.string.team) + " ${teamIndex + 1}",
            color = VolleyColor.White,
            modifier = Modifier.padding()
        )

        team.players.forEach { player ->
            PlayerRow(player)
        }
    }
}

@Stable
@Composable
private fun PlayerRow(
    player: PlayerShort
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {
        VolleyText.BodyRegular(
            text = player.name,
            color = VolleyColor.White,
            modifier = Modifier.weight(1f)
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
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_9_PRO)
@Composable
private fun TeamsScreenPreview() {
    RootContainerForPreview {
        TeamsScreen(
            state = TeamsScreenState(teams = provideMockTeams()),
            paddingFromSystemUi = it,
            eventCallback = {}
        )
    }
}
