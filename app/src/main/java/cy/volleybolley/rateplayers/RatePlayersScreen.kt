package cy.volleybolley.rateplayers

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
import cy.volleybolley.core.domain.model.LevelType
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent.TransparentContainer
import cy.volleybolley.core.presentation.ui.component.VolleyAvatar.CircularAvatar
import cy.volleybolley.core.presentation.ui.component.VolleyButton.ActiveButton
import cy.volleybolley.core.presentation.ui.component.VolleyButton.GroupButtonsForChangeLevel
import cy.volleybolley.core.presentation.ui.component.VolleyProgress
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.games.domain.model.entity.RatingType
import cy.volleybolley.ui.theme.VolleybolleyTheme

@Composable
fun RatePlayersScreen(
    onNavigateBack: () -> Unit,
    viewModel: RatePlayersViewModel,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val effect by viewModel.uiEffect.collectAsStateWithLifecycle(null)

    RatePlayersScreen(
        state = state,
        effect = effect,
        navigateAction = onNavigateBack,
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
    LaunchedEffect(effect) {
        when (effect) {
            RatePlayersEffect.CloseScreen -> navigateAction()
            null -> Unit
        }
    }

    Box(Modifier.fillMaxSize()) {
        TransparentContainer(
            modifier = Modifier.padding(8.dp),
            cornerRadius = 16,
        ) {
            if (state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    VolleyProgress.CircularProgress()
                }
            } else {
                Column(
                    modifier = Modifier.padding(20.dp),
                ) {
                    Header()
                    LazyColumn(
                        modifier = Modifier.padding(top = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        itemsIndexed(state.players) { index, player ->
                            PlayerBox(
                                player = player,
                                onSelected = { ratePosition ->
                                    eventCallback(
                                        RatePlayersEvent.RatePlayer(
                                            index,
                                            ratePosition
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
                            .padding(top = 16.dp)
                    ) {
                        eventCallback(RatePlayersEvent.ConfirmRate)
                    }
                }
            }
        }
    }
}

@Stable
@Composable
private fun Header() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        VolleyText.TitleLarge(
            text = stringResource(R.string.game_completed),
            color = VolleyColor.White
        )
        VolleyText.TitleMedium(
            text = stringResource(R.string.rate_the_players_level),
            color = VolleyColor.White
        )
    }
}

@Stable
@Composable
private fun PlayerBox(
    player: PlayerShortUI,
    onSelected: (RatingType) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        PlayerInfo(player = player, modifier = Modifier.fillMaxWidth())
        GroupButtonsForChangeLevel(
            current = player.rating,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onSelected = onSelected
        )
    }
}

@Stable
@Composable
private fun PlayerInfo(
    player: PlayerShortUI,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        CircularAvatar(avatar = player.avatar, size = 40.dp)
        VolleyText.BodyRegular(text = player.name, color = VolleyColor.White)
        Spacer(Modifier.weight(1f))
        if (player.level != LevelType.UNCONFINED) {
            LevelPill(level = player.level.level.first().toString())
        }
    }
}

@Stable
@Composable
private fun LevelPill(
    modifier: Modifier = Modifier,
    level: String
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .height(23.dp)
            .width(30.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(VolleyColor.GreyDark)
    ) {
        VolleyText.BodyRegular(level, color = VolleyColor.White)
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    device = "spec:width=411dp,height=1000dp,dpi=420"
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
                        playerId = 3,
                        name = "John Smith",
                        level = LevelType.LIGHT,
                        avatar = null,
                        rating = RatingType.DOWN
                    )
                )
            )
            Box(modifier = Modifier.padding(top = 116.dp)) {
                RatePlayersScreen(
                    state = state,
                    effect = null,
                    navigateAction = {},
                    eventCallback = {}
                )
            }
        }
    }
}
