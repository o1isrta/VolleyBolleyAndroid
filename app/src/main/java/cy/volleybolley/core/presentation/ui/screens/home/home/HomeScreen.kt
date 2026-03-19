package cy.volleybolley.core.presentation.ui.screens.home.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyMocks
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.model.state.data.DialogData
import cy.volleybolley.core.presentation.ui.screens.home.home.HomeScreenEffect.NavigateToSearchCourt
import cy.volleybolley.core.presentation.ui.screens.home.home.HomeScreenEffect.RequestNotificationPermission
import cy.volleybolley.core.presentation.ui.screens.home.home.HomeScreenEvent.OnCreateNewGameClick
import cy.volleybolley.core.presentation.ui.screens.home.home.HomeScreenEvent.OnCreateTourneyClick
import cy.volleybolley.core.presentation.ui.screens.home.home.HomeScreenEvent.OnDonateClick
import cy.volleybolley.core.presentation.ui.screens.home.home.HomeScreenEvent.OnFindGameClick
import cy.volleybolley.core.presentation.ui.screens.home.home.HomeScreenEvent.OnNotificationDialogConfirm
import cy.volleybolley.core.presentation.ui.screens.home.home.HomeScreenEvent.OnNotificationDialogDismiss
import cy.volleybolley.core.presentation.ui.screens.home.home.model.DigitIcon
import cy.volleybolley.games.domain.model.event.EventType
import cy.volleybolley.notification.presentation.ui.component.GlobalAlertDialog
import org.koin.androidx.compose.koinViewModel

const val HOME_FIND_GAME_TEXT_WEIGHT = 0.6f
const val HOME_FIND_GAME_COUNT_WEIGHT = 0.4f
const val HOME_CREATE_GAME_BUTTON_ALPHA = 0.85f

@Composable
fun HomeScreen(
    paddingFromSystemUi: PaddingValues,
    onNavigateToSearchCourt: (EventType) -> Unit,
    onRequestNotificationPermission: () -> Unit = {},
    viewModel: HomeScreenViewModel = koinViewModel()
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    val effect = viewModel.uiEffect.collectAsStateWithLifecycle(null).value

    LaunchedEffect(effect) {
        when (effect) {
            is NavigateToSearchCourt -> onNavigateToSearchCourt(effect.eventType)
            RequestNotificationPermission -> onRequestNotificationPermission()
            null -> Unit
        }
    }

    HomeScreen(
        state = state,
        eventCallback = { event -> viewModel.obtainEvent(event) },
        modifier = Modifier.padding(paddingFromSystemUi)
    )
}

@Stable
@Composable
private fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeScreenState,
    eventCallback: (HomeScreenEvent) -> Unit,
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            painter = painterResource(R.drawable.home_wallpaper2),
            modifier = Modifier
                .fillMaxWidth(HOME_CREATE_GAME_BUTTON_ALPHA)
                .aspectRatio(1f)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 20.dp,
                    bottom = 20.dp,
                    start = 8.dp,
                    end = 8.dp
                )
                .align(Alignment.BottomCenter)
        ) {
            CreateNewGameButton(
                locationName = state.location.locationName,
                courtName = state.location.courtName,
                onClick = { eventCallback(OnCreateNewGameClick) }
            )

            Spacer(Modifier.height(8.dp))

            FindGameButton(
                gamesCount = state.nearGamesCount,
                onClick = { eventCallback(OnFindGameClick) }
            )

            Spacer(Modifier.height(8.dp))

            SquareButtonsLine(
                onCreateTourneyButtonClick = { eventCallback(OnCreateTourneyClick) },
                onDonateButtonClick = { eventCallback(OnDonateClick) }
            )
        }
    }

    if (state.showNotificationPermissionDialog) {
        GlobalAlertDialog(
            dialog = DialogData(
                title = stringResource(R.string.notifications),
                message = stringResource(R.string.notifications_alert_dialog),
                onConfirm = { eventCallback(OnNotificationDialogConfirm) },
                onDismiss = { eventCallback(OnNotificationDialogDismiss) }
            ),
            onConfirm = { eventCallback(OnNotificationDialogConfirm) },
            onDismiss = { eventCallback(OnNotificationDialogDismiss) }
        )
    }
}

@Stable
@Composable
private fun CreateNewGameButton(
    modifier: Modifier = Modifier,
    locationName: String,
    courtName: String,
    onClick: () -> Unit,
) {
    val shapeOfButton = remember { RoundedCornerShape(32.dp) }
    Box(
        modifier = Modifier
            .background(
                color = VolleyColor.TurquoiseDark.copy(alpha = 0.86f),
                shape = shapeOfButton
            )
    ) {
        VolleyContainersRootTransparent.TransparentContainer(
            modifier = modifier
                .fillMaxWidth()
                .clickable(
                    indication = null,
                    interactionSource = null,
                    onClick = onClick
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                VolleyText.TitleLarge(
                    text = stringResource(R.string.create_a_new_game),
                    color = VolleyColor.White,
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(Modifier.height(16.dp))

                LocationDescription(
                    locationName = locationName,
                    courtName = courtName,
                    weatherComponent = {},
                )
            }
        }
    }
}

@Stable
@Composable
private fun LocationDescription(
    modifier: Modifier = Modifier,
    locationName: String,
    courtName: String,
    weatherComponent: @Composable BoxScope.() -> Unit
) {
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_pointer_map),
                contentDescription = null,
                tint = VolleyColor.OrangeHard
            )

            Spacer(Modifier.width(8.dp))

            Column(Modifier.weight(1f)) {
                VolleyText.BodyBold(
                    text = courtName,
                    color = VolleyColor.White,
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                VolleyText.BodyLight(
                    text = locationName,
                    color = VolleyColor.White,
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
            }

            Box(content = weatherComponent)
        }
    }
}

@Stable
@Composable
private fun FindGameButton(
    modifier: Modifier = Modifier,
    gamesCount: Int,
    onClick: () -> Unit,
) {
    VolleyContainersRootTransparent.TransparentContainer(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                indication = null,
                interactionSource = null,
                onClick = onClick
            )
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .weight(HOME_FIND_GAME_TEXT_WEIGHT)
                    .padding(
                        top = 12.dp,
                        start = 12.dp,
                        end = 6.dp,
                        bottom = 4.dp
                    )
            ) {
                VolleyText.TitleLarge(
                    text = stringResource(R.string.find_a_game),
                    color = VolleyColor.White,
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                VolleyText.BodyRegular(
                    text = stringResource(R.string.near_you),
                    color = VolleyColor.White,
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                )

                Box(
                    modifier = Modifier
                        .padding(start = 36.dp, end = 24.dp)
                        .height(40.dp)
                ) {
                    Image(
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        painter = painterResource(R.drawable.arrow_horizontal_up),
                    )
                }
            }

            GamesAvailableBlock(
                gamesCount = gamesCount,
                modifier = Modifier.weight(HOME_FIND_GAME_COUNT_WEIGHT)
            )
        }
    }
}

@Stable
@Composable
private fun GamesAvailableBlock(
    modifier: Modifier = Modifier,
    gamesCount: Int = 0,
) {
    val digitsStringValuesList = gamesCount.toString().toList()
    val topGapForDigits = when (digitsStringValuesList.size) {
        3 -> 10
        4 -> 16
        else -> 0
    }

    Box(
        modifier = modifier
            .height(100.dp)
            .background(
                color = VolleyColor.White,
                shape = RoundedCornerShape(28.dp)
            )
            .padding(
                horizontal = 12.dp,
                vertical = 8.dp
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(
                        top = topGapForDigits.dp,
                        bottom = (10 + topGapForDigits).dp
                    )
            ) {
                digitsStringValuesList.forEach { digitString ->
                    Image(
                        contentDescription = null,
                        painter = painterResource(DigitIcon.getIconResByString(digitString)),
                        contentScale = ContentScale.Inside,
                    )
                }
            }

            VolleyText.BodyBoldSmall(
                text = stringResource(R.string.games_available),
                color = VolleyColor.TextDark,
                textAlign = TextAlign.Center,
                maxLines = 1,
            )
        }
    }
}

@Composable
private fun SquareButtonsLine(
    onCreateTourneyButtonClick: () -> Unit,
    onDonateButtonClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        CreateTourneyButton(
            onClick = onCreateTourneyButtonClick,
            modifier = Modifier.weight(1f)
        )
        Spacer(Modifier.width(8.dp))
        DonateButton(
            onClick = onDonateButtonClick,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun CreateTourneyButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val shape = remember { RoundedCornerShape(32.dp) }
    val insidePaddings = remember {
        PaddingValues(
            top = 20.dp,
            start = 20.dp
        )
    }

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .background(color = VolleyColor.YellowPro, shape = shape)
            .clickable(
                indication = null,
                interactionSource = null,
                onClick = onClick
            )
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.BottomEnd),
            painter = painterResource(R.drawable.crown_1024),
            contentDescription = null
        )

        VolleyText.TitleLarge(
            modifier = Modifier
                .padding(insidePaddings)
                .rotate(-8f),
            text = stringResource(R.string.create_a_tourney),
            color = VolleyColor.TextDark
        )
    }
}

@Composable
private fun DonateButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val insidePaddings = remember {
        PaddingValues(
            top = 28.dp,
            start = 30.dp
        )
    }

    VolleyContainersRootTransparent.TransparentContainer(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable(
                indication = null,
                interactionSource = null,
                onClick = onClick
            )
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.BottomEnd),
            painter = painterResource(R.drawable.heart_1024),
            contentDescription = null
        )

        VolleyText.TitleLarge(
            modifier = Modifier
                .padding(insidePaddings)
                .rotate(-8f),
            text = stringResource(R.string.donate),
            color = VolleyColor.White
        )
    }
}

@Preview
@Composable
private fun PreviewHomeScreen() {
    VolleyContainersRootTransparent.Root {
        Box(
            contentAlignment = Alignment.TopStart,
            modifier = Modifier
                .fillMaxSize()
                .background(VolleyColor.TurquoiseDark)
        ) {
            val state = HomeScreenState(
                nearGamesCount = 13,
                location = VolleyMocks.mockLocation
            )
            HomeScreen(
                state = state,
                eventCallback = {}
            )
        }
    }
}

@Preview
@Composable
private fun PreviewFindGameButton() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(VolleyColor.TurquoiseDark)
    ) {
        Column {
            FindGameButton(
                gamesCount = 0,
                onClick = {}
            )
            Spacer(Modifier.height(20.dp))
            FindGameButton(
                gamesCount = 22,
                onClick = {}
            )
            Spacer(Modifier.height(20.dp))
            FindGameButton(
                gamesCount = 222,
                onClick = {}
            )
            Spacer(Modifier.height(20.dp))
            FindGameButton(
                gamesCount = 2222,
                onClick = {}
            )
        }
    }
}
