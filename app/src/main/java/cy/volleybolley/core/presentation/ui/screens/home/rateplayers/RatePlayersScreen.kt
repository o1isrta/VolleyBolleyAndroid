package cy.volleybolley.core.presentation.ui.screens.home.rateplayers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent.TransparentContainer
import cy.volleybolley.core.presentation.ui.component.VolleyAvatar.CircularAvatar
import cy.volleybolley.core.presentation.ui.component.VolleyButton.ActiveButton
import cy.volleybolley.core.presentation.ui.component.VolleyButton.GroupButtonsForChangeLevel
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.ui.theme.VolleybolleyTheme

@Composable

fun RatePlayersScreen(
    navController: NavHostController,
    viewModel: RatePlayersViewModel,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val effect by viewModel.effects.collectAsStateWithLifecycle(null)

    RatePlayersScreen(
        state = state,
        effect = effect,
        navigateAction = {
            navController.popBackStack()
        },
        eventCallback = { event ->
            viewModel.obtainEvent(event)
        }
    )
}

@Composable
private fun RatePlayersScreen(
    state: RatePlayersState,
    effect: RatePlayersEffect?,
    navigateAction: () -> Unit,
    eventCallback: (RatePlayersEvent) -> Unit,
) {
    LaunchedEffect(Unit) {
        when (effect) {
            RatePlayersEffect.CloseScreen -> navigateAction
            null -> Unit
        }
    }

    Box(Modifier.fillMaxSize()) {
        TransparentContainer(
            modifier = Modifier.padding(
                start = VolleyDimens.DIMEN_8.dp,
                end = VolleyDimens.DIMEN_8.dp,
                top = VolleyDimens.DIMEN_116.dp
            ),
            cornerRadius = VolleyDimens.DIMEN_16,
        ) {
            if (state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                Column(
                    modifier = Modifier.padding(VolleyDimens.DIMEN_20.dp)
                ) {
                    Header()
                    LazyColumn(
                        modifier = Modifier,
                    ) {
                        itemsIndexed(state.players) { index, player ->
                            PlayerBox(
                                player = player,
                                onSelected = {
                                    eventCallback(
                                        RatePlayersEvent.RatePlayer(
                                            player.playerId,
                                            RatingType.entries[it - 1]
                                        )
                                    )
                                }
                            )
                        }
                    }
                    ActiveButton(
                        text = stringResource(R.string.rate_players),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = VolleyDimens.DIMEN_16.dp)
                    ) {
                        eventCallback(RatePlayersEvent.ConfirmRate)
                    }
                }
            }
        }
    }
}

@Composable
fun Header() {
    Column(
        verticalArrangement = Arrangement.spacedBy(VolleyDimens.DIMEN_16.dp)
    ) {
        VolleyText.TitleLarge(
            text = stringResource(R.string.game_completed), color = VolleyColor.White
        )
        VolleyText.TitleMedium(
            text = stringResource(R.string.rate_the_players_level), color = VolleyColor.White
        )
    }
}

@Composable
fun PlayerBox(
    player: PlayerShortUI,
    onSelected: (Int) -> Unit
) {
    Column(
        modifier = Modifier.padding(top = VolleyDimens.DIMEN_16.dp),
        verticalArrangement = Arrangement.spacedBy(VolleyDimens.DIMEN_12.dp)

    ) {
        PlayerInfo(player = player)
        GroupButtonsForChangeLevel(
            checkId = player.rating.checkId,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onSelected = onSelected
        )
    }
}

@Composable
fun PlayerInfo(
    player: PlayerShortUI
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        CircularAvatar(avatar = player.avatar, size = 32.dp)
        VolleyText.BodyRegular(text = player.name, color = VolleyColor.White)
        Spacer(Modifier.weight(1f))
        LevelPill(level = player.level.level.firstOrNull()?.toString() ?: "")
    }
}

@Composable
private fun LevelPill(level: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .height(VolleyDimens.DIMEN_23.dp)
            .width(VolleyDimens.DIMEN_30.dp)
            .clip(RoundedCornerShape(VolleyDimens.DIMEN_10.dp))
            .background(VolleyColor.GreyDark)
    ) {
        VolleyText.BodyRegular(level, color = VolleyColor.White)
    }
}

@Preview(
    showBackground = true, showSystemUi = true, device = "spec:width=411dp,height=1000dp,dpi=420"
)
@Composable
private fun RatePlayersPreview() {
    VolleybolleyTheme {
        Box(
            Modifier
                .fillMaxSize()
                .background(VolleyColor.TurquoiseDark)
        ) {

            val state = RatePlayersState(
                isLoading = false,
                players = listOf(
                    PlayerShortUI(
                        playerId = 1,
                        name = "Kristina Popova",
                        level = LevelType.LIGHT,
                        avatar = null,
                        rating = RatingType.CONFIRM
                    ),
                    PlayerShortUI(
                        playerId = 2,
                        name = "Jane Dow",
                        level = LevelType.HARD,
                        avatar = null,
                        rating = RatingType.UP
                    ),
                    PlayerShortUI(
                        playerId = 2,
                        name = "John Smith",
                        level = LevelType.LIGHT,
                        avatar = null,
                        rating = RatingType.DOWN
                    )
                )
            )
            RatePlayersScreen(
                state = state,
                effect = null,
                navigateAction = {},
                eventCallback = {}
            )
        }
    }
}
