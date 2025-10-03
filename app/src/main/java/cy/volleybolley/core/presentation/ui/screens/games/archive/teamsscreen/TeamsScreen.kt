package cy.volleybolley.core.presentation.ui.screens.games.archive.teamsscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.screens.games.archive.datamodel.PlayerShort
import cy.volleybolley.core.presentation.ui.screens.games.archive.datamodel.Team

@Composable
fun TeamsScreen(
    navController: NavHostController
) {
    TeamsScreen(
        teams = listOf(
            Team(
                0, listOf(
                    PlayerShort(0, "Anton Ivanov", "H"),
                    PlayerShort(1, "Aleksandr Abramov", "H")
                )
            ),
            Team(
                1,
                listOf(
                    PlayerShort(0, "Anya Levan", "H"),
                    PlayerShort(1, "Alina Lyubimova", "H")
                )
            ),
            Team(
                2,
                listOf(
                    PlayerShort(0, "Maxim Petrov", "H"),
                    PlayerShort(1, "Julia Petrova", "H")
                )
            ),
            Team(
                3,
                listOf(
                    PlayerShort(0, "Tatiana Kalinina", "H"),
                    PlayerShort(1, "Artem Artemov", "H")
                )
            )
        ),
        onBackClick = { navController.popBackStack() }
    )
}

@Composable
private fun TeamsScreen(
    teams: List<Team>,
    onBackClick: () -> Unit
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
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
                TeamsHeader(
                    onBackClick
                )

                teams.forEachIndexed { index, team ->
                    TeamBlock(
                        team = team,
                        teamIndex = index
                    )
                }
            }
        }
    }
}

@Composable
private fun TeamsHeader(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(R.drawable.arrow_left_white),
            contentDescription = null,
            tint = VolleyColor.White,
            modifier = Modifier.clickable {
                onBackClick()
            }
        )


        VolleyText.TitleLarge(
            text = stringResource(R.string.teams),
            color = VolleyColor.White,
            modifier = Modifier.align(Alignment.Center)
        )

    }
}

@Composable
private fun TeamBlock(
    team: Team,
    teamIndex: Int
) {
    val topPadding = if (teamIndex == 0) VolleyDimens.DIMEN_16.dp else VolleyDimens.DIMEN_20.dp

    Column(
        modifier = Modifier
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
                text = player.level,
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
