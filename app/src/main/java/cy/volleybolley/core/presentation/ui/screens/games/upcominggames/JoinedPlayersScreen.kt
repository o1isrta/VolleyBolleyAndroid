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
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.component.VolleyTopBar.TopBarWithBackButton
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyMocks
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.games.domain.model.entity.PlayerShort
import cy.volleybolley.games.domain.model.event.tournament.TournamentDetails
import cy.volleybolley.ui.theme.VolleybolleyTheme

@Composable
fun JoinedPlayersScreen(
    navController: NavHostController,
    paddingFromSystemUi: PaddingValues,
    tournamentDetails: TournamentDetails,
) {
    JoinedPlayersScreen(
        modifier = Modifier
            .padding(paddingFromSystemUi)
            .fillMaxSize(),
        tournamentDetails = tournamentDetails,
        navigateBack = { navController.popBackStack() }
    )
}

@Stable
@Composable
private fun JoinedPlayersScreen(
    modifier: Modifier = Modifier,
    tournamentDetails: TournamentDetails,
    navigateBack: () -> Unit,
) {
    Box(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        VolleyContainersRootTransparent.TransparentContainer(
            cornerRadius = VolleyDimens.DIMEN_32,
            modifier = Modifier
                .fillMaxWidth()
                .padding(VolleyDimens.DIMEN_8.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(VolleyDimens.DIMEN_8.dp),
                modifier = Modifier.padding(VolleyDimens.DIMEN_20.dp)
            ) {
                TopBarWithBackButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = VolleyDimens.DIMEN_8.dp),
                    title = stringResource(R.string.players),
                    onBackNavigationRequested = navigateBack
                )

                val players = tournamentDetails.teams[0].players
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
private fun PlayersRow(index: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        VolleyText.BodyRegular(
            text = "$index. ${stringResource(R.string.free_spot)}",
            color = VolleyColor.White,
            modifier = Modifier
                .weight(1f)
                .padding(start = VolleyDimens.DIMEN_8.dp)
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
                modifier = Modifier
                    .fillMaxSize()
                    .padding(VolleyDimens.DIMEN_8.dp),
                tournamentDetails = VolleyMocks.mockTournament,
                navigateBack = {}
            )
        }
    }
}
