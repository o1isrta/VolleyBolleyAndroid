package cy.volleybolley.core.presentation.ui.screens.games.archive.pastgamescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.component.VolleyAvatar
import cy.volleybolley.core.presentation.ui.component.VolleyButton
import cy.volleybolley.core.presentation.ui.component.VolleyProgress
import cy.volleybolley.core.presentation.ui.component.VolleyTopBar
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.screens.games.archive.pastgamescreen.effect.PastGameEffect
import cy.volleybolley.core.presentation.ui.screens.games.archive.pastgamescreen.event.PastGameEvent
import cy.volleybolley.core.presentation.ui.screens.games.archive.pastgamescreen.model.PastGameState
import cy.volleybolley.core.presentation.ui.screens.games.archive.pastgamescreen.viewmodel.PastGameViewModel
import cy.volleybolley.core.presentation.ui.screens.games.archive.util.DataTimeRangeFormatter
import cy.volleybolley.core.presentation.ui.screens.games.archive.util.openMap
import cy.volleybolley.courts.domain.model.Location
import cy.volleybolley.games.domain.model.entity.Host
import cy.volleybolley.games.domain.model.entity.PlayerShort
import cy.volleybolley.games.domain.model.event.game.GameDetails
import cy.volleybolley.ui.theme.VolleybolleyTheme

@Composable
fun PastGameScreen(
    navController: NavHostController,
    viewModel: PastGameViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val effect by viewModel.uiEffect.collectAsStateWithLifecycle(null)

    PastGameScreen(
        state = state,
        effect = effect,
        onBackClick = { navController.popBackStack() },
        eventCallback = { event ->
            viewModel.obtainEvent(event)
        }
    )
}

@Composable
private fun PastGameScreen(
    state: PastGameState,
    effect: PastGameEffect?,
    onBackClick: () -> Unit,
    eventCallback: (PastGameEvent) -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(effect) {
        effect?.let {
            when (it) {
                PastGameEffect.NavigateBack -> onBackClick
                is PastGameEffect.OpenMap -> context.openMap(it.location)
            }
        }
    }

    Render(
        state = state,
        onBackClick = onBackClick,
        eventCallback = eventCallback
    )
}

@Stable
@Composable
private fun Render(
    state: PastGameState,
    onBackClick: () -> Unit,
    eventCallback: (PastGameEvent) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = VolleyDimens.DIMEN_8.dp)
            .verticalScroll(rememberScrollState())
    ) {
        when (state) {
            PastGameState.Loading -> ShowLoader()

            PastGameState.Error -> ShowErrorPlaceholder(
                onBackClick = onBackClick,
                onButtonClick = { eventCallback(PastGameEvent.Refresh) }
            )

            is PastGameState.Content -> ShowPastGameDetails(
                game = state.game,
                onBackClick = onBackClick,
                onMapClick = { eventCallback(PastGameEvent.OnMapClick(state.game.courtLocation)) },
            )
        }
    }
}

@Stable
@Composable
private fun ShowPastGameDetails(
    game: GameDetails,
    onBackClick: () -> Unit,
    onMapClick: (Location) -> Unit,
    modifier: Modifier = Modifier
) {
    VolleyContainersRootTransparent.TransparentContainer(
        cornerRadius = VolleyDimens.DIMEN_32
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(VolleyDimens.DIMEN_16.dp),
            modifier = modifier
                .fillMaxWidth()
                .padding(VolleyDimens.DIMEN_20.dp)
                .scrollable(rememberScrollState(), Orientation.Vertical)
        ) {
            PastGameHeader(
                onBackClick = onBackClick,
                modifier = Modifier.fillMaxWidth()
            )
            HostInfoBlock(host = game.host, message = game.message)
            DividerGlass()
            AboutGameBlock(
                courtLocation = game.courtLocation,
                startTime = game.startTime,
                endTime = game.endTime,
                level = game.levels[0],
                gender = game.gender,
                onMapClick = onMapClick
            )
            DividerGlass()
            PaymentBlock(
                paymentType = game.paymentType.name,
                paymentAccount = game.paymentAccount,
                currencyType = game.currencyType,
                pricePerPerson = game.pricePerPerson
            )
            DividerGlass()
            PlayersBlock(game.players)
        }
    }
}

@Stable
@Composable
private fun PlayersBlock(
    players: List<PlayerShort>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(VolleyDimens.DIMEN_16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        VolleyText.TitleMedium(
            text = stringResource(R.string.joined_players_loading),
            color = VolleyColor.White,
            modifier = Modifier.align(Alignment.Start)
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
private fun PaymentBlock(
    paymentType: String,
    paymentAccount: String,
    currencyType: String,
    pricePerPerson: String
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(VolleyDimens.DIMEN_12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        VolleyText.TitleMedium(
            text = stringResource(R.string.payment),
            color = VolleyColor.White,
            modifier = Modifier.align(Alignment.Start)
        )

        Row {
            VolleyText.BodyRegular(
                text = if (paymentType == "Cash") {
                    stringResource(R.string.cash)
                } else {
                    stringResource(
                        R.string.payment_type_and_account,
                        paymentType,
                        paymentAccount
                    )
                },
                color = VolleyColor.White
            )
        }

        VolleyContainersRootTransparent.TransparentContainer(
            cornerRadius = VolleyDimens.DIMEN_16
        ) {
            VolleyText.BodyRegular(
                text = stringResource(R.string.per_person) + " $pricePerPerson$currencyType",
                color = VolleyColor.White,
                modifier = Modifier
                    .padding(VolleyDimens.DIMEN_16.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Stable
@Composable
private fun AboutGameBlock(
    courtLocation: Location,
    startTime: String,
    endTime: String,
    level: String,
    gender: String,
    onMapClick: (Location) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(VolleyDimens.DIMEN_16.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        VolleyText.TitleMedium(
            text = stringResource(R.string.about_game),
            color = VolleyColor.White,
            modifier = Modifier.align(Alignment.Start)
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(R.drawable.ic_nav_yellow_arrow),
                contentDescription = null,
                tint = VolleyColor.OrangeHard,
                modifier = Modifier.size(VolleyDimens.DIMEN_16.dp)
            )
            Spacer(modifier = Modifier.width(VolleyDimens.DIMEN_8.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                VolleyText.BodyBold(courtLocation.courtName, color = VolleyColor.White)
                VolleyText.BodyLight(courtLocation.locationName, color = VolleyColor.White)
            }
            VolleyButton.ActiveButtonMap(
                text = stringResource(R.string.map),
                onClick = {
                    onMapClick(courtLocation)
                },
                paddingValues = PaddingValues(VolleyDimens.DIMEN_16.dp, VolleyDimens.DIMEN_12.dp)
            )

        }

        Row {
            VolleyText.BodyBold(
                text = stringResource(R.string.`when`),
                color = VolleyColor.White,
                modifier = Modifier.padding(end = VolleyDimens.DIMEN_4.dp)
            )

            val (dateText, timeText) = remember(startTime, endTime) {
                DataTimeRangeFormatter.format(startTime, endTime)
            }

            VolleyText.BodyRegular(
                text = "$dateText, $timeText",
                color = VolleyColor.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
        }

        Row {
            VolleyText.BodyBold(
                text = stringResource(R.string.level),
                color = VolleyColor.White,
                modifier = Modifier.padding(end = VolleyDimens.DIMEN_4.dp)
            )

            VolleyText.BodyRegular(
                text = level,
                color = VolleyColor.White,
                modifier = Modifier.weight(1f)
            )
        }

        Row {
            VolleyText.BodyBold(
                text = stringResource(R.string.gender),
                color = VolleyColor.White,
                modifier = Modifier.padding(end = VolleyDimens.DIMEN_4.dp)
            )

            VolleyText.BodyRegular(
                text = gender,
                color = VolleyColor.White,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Stable
@Composable
private fun HostInfoBlock(host: Host, message: String) {
    Column(
        verticalArrangement = Arrangement.spacedBy(VolleyDimens.DIMEN_8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        VolleyText.TitleMedium(
            text = stringResource(R.string.game_host),
            color = VolleyColor.White,
            modifier = Modifier.align(Alignment.Start)
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            VolleyAvatar.CircularAvatar(
                host.avatar,
                VolleyDimens.DIMEN_40.dp
            )

            VolleyText.BodyRegular(
                text = host.name,
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
                    text = host.level.name.first().toString(),
                    color = VolleyColor.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        VolleyContainersRootTransparent.TransparentContainer(
            cornerRadius = VolleyDimens.DIMEN_16
        ) {
            VolleyText.BodyRegular(
                text = message,
                color = VolleyColor.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(VolleyDimens.DIMEN_16.dp)
            )
        }
    }
}

@Stable
@Composable
private fun DividerGlass() {
    HorizontalDivider(
        thickness = VolleyDimens.DIMEN_1.dp,
        color = VolleyColor.White.copy(alpha = 0.25f)
    )
}

@Stable
@Composable
private fun ShowErrorPlaceholder(
    onBackClick: () -> Unit,
    onButtonClick: () -> Unit
) {
    VolleyContainersRootTransparent.TransparentContainer(
        cornerRadius = VolleyDimens.DIMEN_32
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(VolleyDimens.DIMEN_16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(VolleyDimens.DIMEN_20.dp)
        ) {
            PastGameHeader(
                onBackClick = onBackClick,
                modifier = Modifier.fillMaxWidth()
            )
            PlaceholderMessage()
            RefreshButton(onButtonClick)
        }
    }
}

@Stable
@Composable
private fun PastGameHeader(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    VolleyTopBar.TopBarWithBackButton(
        modifier = modifier,
        title = stringResource(R.string.past_game),
        onBackNavigationRequested = onBackClick
    )
}

@Stable
@Composable
private fun PlaceholderMessage(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.smiley_placeholder),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .padding(
                    top = VolleyDimens.DIMEN_8.dp,
                    bottom = VolleyDimens.DIMEN_16.dp
                )
                .size(VolleyDimens.DIMEN_160.dp)
        )

        VolleyText.BodyRegular(
            text = stringResource(R.string.something_went_wrong),
            color = VolleyColor.White,
            textAlign = TextAlign.Center
        )
    }
}

@Stable
@Composable
private fun RefreshButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    VolleyButton.ActiveButton(
        text = stringResource(R.string.refresh),
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
    )
}

@Stable
@Composable
private fun ShowLoader(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        VolleyProgress.CircularProgress()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PastGameScreenPreview() {
    VolleybolleyTheme {
        Box(
            Modifier
                .fillMaxSize()
                .background(VolleyColor.TurquoiseDark)
        ) {
            PastGameScreen(
                state = PastGameState.Content(),
                effect = null,
                onBackClick = {},
                eventCallback = {}
            )
        }
    }
}
