package cy.volleybolley.profile.presentation.ui.screens.players

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cy.volleybolley.R
import cy.volleybolley.core.presentation.RootContainerForPreview
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.VolleySimpleComponent
import cy.volleybolley.core.presentation.ui.VolleyTextFieldGradient
import cy.volleybolley.core.presentation.ui.component.VolleyAvatar
import cy.volleybolley.core.presentation.ui.component.VolleyProgress
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.players.domain.model.Player
import cy.volleybolley.profile.presentation.ui.screens.players.PlayersScreenEvent.ClickOnAllPlayers
import cy.volleybolley.profile.presentation.ui.screens.players.PlayersScreenEvent.ClickOnBackFromPlayers
import cy.volleybolley.profile.presentation.ui.screens.players.PlayersScreenEvent.ClickOnFavoritePlayers
import cy.volleybolley.profile.presentation.ui.screens.players.PlayersScreenEvent.ClickOnListItem
import cy.volleybolley.profile.presentation.ui.screens.players.PlayersScreenEvent.ClickOnSearchButton
import cy.volleybolley.profile.presentation.ui.screens.players.PlayersScreenEvent.SearchTextChanged

@Composable
fun PlayersScreen(
    onNavigateToPlayerProfile: (Int) -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: PlayersScreenViewModel,
    paddingFromSystemUi: PaddingValues,
) {
    LaunchedEffect(Unit) {
        viewModel.handleBackPlayerId()
    }

    val context = LocalContext.current
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    val effect = viewModel.uiEffect.collectAsStateWithLifecycle(null).value

    LaunchedEffect(effect) {
        when (effect) {
            is PlayersScreenEffect.NavigateFromPlayersScreen -> {
                when (val route = effect.route) {
                    is cy.volleybolley.core.presentation.ui.navigation.PlayerProfileRoute -> {
                        onNavigateToPlayerProfile(route.playerId)
                    }
                    else -> onNavigateBack()
                }
            }

            is PlayersScreenEffect.ShowToast -> {
                Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            }

            null -> {}
        }
    }

    PlayersScreen(
        state = state,
        eventCallback = { event -> viewModel.obtainEvent(event) },
        modifier = Modifier.padding(paddingFromSystemUi)
    )
}

@Composable
private fun PlayersScreen(
    modifier: Modifier = Modifier,
    state: PlayersScreenState,
    eventCallback: (PlayersScreenEvent) -> Unit,
) {
    VolleyContainersRootTransparent.TransparentContainer(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
        ) {
            VolleySimpleComponent.TitleWithBackArrow(
                title = stringResource(R.string.players),
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                onBackClick = { eventCallback(ClickOnBackFromPlayers) }
            )

            VolleyTextFieldGradient.SearchField(
                text = state.searchText,
                actionToTransferContent = { fieldText -> eventCallback(SearchTextChanged(fieldText)) },
                actionOnInputComplete = { playerName -> eventCallback(ClickOnSearchButton(playerName.trim())) }
            )

            PlayersListModeSwitch(
                paddingValues = PaddingValues(vertical = 16.dp),
                showAllPlayers = state.showAllPlayers,
                onAllClick = { eventCallback(ClickOnAllPlayers) },
                onFavoriteClick = { eventCallback(ClickOnFavoritePlayers) },
            )

            when (state.isLoading) {
                false -> {
                    if (state.players.isEmpty()) {
                        VolleyText.BodyRegular(
                            text = stringResource(R.string.no_players_found),
                            color = VolleyColor.White,
                            maxLines = 1,
                        )
                    } else {
                        LazyColumn {
                            itemsIndexed(state.players) { index, player ->
                                PlayersListItem(player) { playerId -> eventCallback(ClickOnListItem(playerId)) }
                                if (index < state.players.lastIndex) Spacer(Modifier.height(16.dp))
                            }
                        }
                    }
                }

                else -> {
                    VolleyProgress.CircularProgress(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun PlayersListItem(
    player: Player,
    onItemClick: (Int) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = null,
                indication = null,
                onClick = { onItemClick(player.id) }
            )
    ) {
        AvatarSmall(player.avatarUrl)
        Spacer(Modifier.width(8.dp))
        VolleyText.BodyRegular(
            text = "${player.firstName} ${player.lastName}",
            color = VolleyColor.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .weight(1f)
        )
        Spacer(Modifier.width(8.dp))
        VolleySimpleComponent.FavoriteMark(isFavorite = player.isFavorite)
        Spacer(Modifier.width(8.dp))
        LevelContainer(levelValue = player.level)
    }
}

@Composable
private fun AvatarSmall(
    avatarUrl: String? = null
) {
    VolleyAvatar.CircularAvatar(
        avatar = avatarUrl,
        size = 40.dp
    )
}

@Composable
private fun LevelContainer(
    paddingValues: PaddingValues = PaddingValues(),
    levelValue: String,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(paddingValues)
            .size(
                width = 30.dp,
                height = 23.dp
            )
            .background(
                color = VolleyColor.GreyDark,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        VolleyText.BodyRegular(
            text = levelValue.take(1),
            color = VolleyColor.White,
            maxLines = 1,
        )
    }
}

@Composable
private fun PlayersListModeSwitch(
    paddingValues: PaddingValues = PaddingValues(),
    height: Int = 32,
    cornerRadius: Int = 16,
    showAllPlayers: Boolean,
    onAllClick: () -> Unit,
    onFavoriteClick: () -> Unit,
) {
    val gradientBrush = remember {
        Brush.verticalGradient(
            colors = listOf(
                VolleyColor.YellowForGradient,
                VolleyColor.GreenForGradient
            )
        )
    }

    val shape = RoundedCornerShape(cornerRadius.dp)

    Box(
        modifier = Modifier
            .padding(paddingValues)
            .height(height.dp)
            .fillMaxWidth()
            .background(gradientBrush, shape)
    ) {
        Row(Modifier.padding(2.dp)) {
            ChangedBackgroundBox(
                text = stringResource(R.string.all_players),
                shape = shape,
                showBackground = showAllPlayers,
                onTextClick = onAllClick,
                modifier = Modifier.weight(1f)
            )

            Spacer(Modifier.width(8.dp))

            ChangedBackgroundBox(
                text = stringResource(R.string.favorites),
                shape = shape,
                showBackground = !showAllPlayers,
                onTextClick = onFavoriteClick,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun ChangedBackgroundBox(
    modifier: Modifier = Modifier,
    text: String,
    shape: Shape,
    showBackground: Boolean,
    onTextClick: () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxHeight()
            .background(
                color = if (showBackground) VolleyColor.White else Color.Transparent,
                shape = shape
            )
    ) {
        VolleyText.BodyRegular(
            text = text,
            color = VolleyColor.TextDark,
            maxLines = 1,
            modifier = Modifier.clickable(
                interactionSource = null,
                indication = null,
                onClick = onTextClick
            )
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_9_PRO)
@Composable
private fun PreviewPlayersScreen() {
    RootContainerForPreview(showTopBar = false,showBottomBar = false) {
        val mockPlayers: List<Player> = listOf(
            Player(
                id = 1,
                firstName = "Иван",
                lastName = "Иванов",
                avatarUrl = null,
                isFavorite = true,
                level = "LIGHT",
                gender = "Men"
            ),
            Player(
                id = 2,
                firstName = "Анна",
                lastName = "Петрова",
                avatarUrl = null,
                isFavorite = false,
                level = "MEDIUM",
                gender = "Men"
            ),
            Player(
                id = 3,
                firstName = "Сергей",
                lastName = "Смирнов",
                avatarUrl = null,
                isFavorite = true,
                level = "HARD",
                gender = "Men"
            ),
            Player(
                id = 4,
                firstName = "Елена",
                lastName = "Васильева",
                avatarUrl = null,
                isFavorite = false,
                level = "PRO",
                gender = "Men"
            ),
        )
        PlayersScreen(
            state = PlayersScreenState(players = mockPlayers, isLoading = false),
            eventCallback = {}
        )
    }
}
