package cy.volleybolley.core.presentation.ui.screens.games.archive.teamsscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.component.VolleyTopBar.TopBarWithBackButton
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.screens.games.archive.teamsscreen.model.TeamsScreenState
import cy.volleybolley.games.domain.model.entity.PlayerShort
import cy.volleybolley.games.domain.model.entity.Team

@Composable
fun TeamsScreen(
    navController: NavHostController
) {
    TeamsScreen(
        state = TeamsScreenState.Teams(),
        onBackClick = { navController.popBackStack() },
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    )
}

@Composable
private fun TeamsScreen(
    modifier: Modifier = Modifier,
    state: TeamsScreenState,
    onBackClick: () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        VolleyContainersRootTransparent.TransparentContainer(
            cornerRadius = VolleyDimens.DIMEN_32,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = VolleyDimens.DIMEN_8.dp
                )
        ) {
            Column(
                modifier = Modifier
                    .padding(VolleyDimens.DIMEN_20.dp)
            ) {
                when (state) {
                    is TeamsScreenState.Players -> {
                        PlayersList(
                            modifier = Modifier.fillMaxWidth(),
                            players = state.players[0].players,
                            onBackClick = onBackClick
                        )
                    }

                    is TeamsScreenState.Teams -> {
                        TeamsList(
                            modifier = Modifier.fillMaxWidth(),
                            teams = state.teams,
                            onBackClick = onBackClick
                        )
                    }
                }
            }
        }
    }
}

@Stable
@Composable
private fun PlayersList(
    modifier: Modifier = Modifier,
    players: List<PlayerShort>,
    onBackClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(VolleyDimens.DIMEN_8.dp),
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
                .padding(start = VolleyDimens.DIMEN_8.dp)
        )

        Box(
            modifier = Modifier
                .size(VolleyDimens.DIMEN_32.dp, VolleyDimens.DIMEN_20.dp)
                .clip(RoundedCornerShape(VolleyDimens.DIMEN_8.dp))
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

@Stable
@Composable
private fun TeamBlock(
    modifier: Modifier = Modifier,
    team: Team,
    teamIndex: Int
) {
    val topPadding = if (teamIndex == 0) VolleyDimens.DIMEN_16.dp else VolleyDimens.DIMEN_20.dp

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
            .padding(top = VolleyDimens.DIMEN_8.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {
        VolleyText.BodyRegular(
            text = player.name,
            color = VolleyColor.White,
            modifier = Modifier.weight(1f)
        )

        Box(
            modifier = Modifier
                .size(VolleyDimens.DIMEN_32.dp, VolleyDimens.DIMEN_20.dp)
                .clip(RoundedCornerShape(VolleyDimens.DIMEN_8.dp))
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

@Preview(showBackground = true)
@Composable
private fun TeamsScreenPreview() {
    VolleyContainersRootTransparent.Root {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(VolleyColor.TurquoiseDark)
        )
        TeamsScreen(
            rememberNavController()
        )
    }
}
