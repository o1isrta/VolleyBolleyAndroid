package cy.volleybolley.core.presentation.ui.screens.home.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.navigation.NavHostController
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.model.VolleyUiUtil
import cy.volleybolley.core.presentation.ui.navigation.NavMap
import cy.volleybolley.core.presentation.ui.navigation.model.DigitIcon
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeScreenViewModel = koinViewModel(),
    finisher: () -> Unit,
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    val effect = viewModel.uiEffect.collectAsStateWithLifecycle(null).value

    HomeScreen(
        state = state,
        effect = effect,
        navigateAction = { route -> navController.navigate(route) },
        eventCallback = { event -> viewModel.obtainEvent(event) }
    )

    BackHandler { finisher() }
}

@Composable
private fun HomeScreen(
    state: HomeScreenState,
    effect: HomeScreenEffect?,
    navigateAction: (NavMap) -> Unit,
    eventCallback: (HomeScreenEvent) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            painter = painterResource(R.drawable.home_wallpaper),
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .aspectRatio(1f)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = VolleyDimens.DIMEN_20.dp,
                    bottom = VolleyDimens.DIMEN_20.dp,
                    start = VolleyDimens.DIMEN_8.dp,
                    end = VolleyDimens.DIMEN_8.dp
                )
                .align(Alignment.BottomCenter)
        ) {
            SquareButtonsLine(
                onCreateTourneyButtonClick = {},
                onDonateButtonClick = {}
            )
        }
    }

    LaunchedEffect(effect) {
        when(effect){
            is HomeScreenEffect.NavigateFromHomeScreen -> navigateAction(effect.route)
            null -> Unit
        }
    }
}

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
                .padding(VolleyDimens.DIMEN_8.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .weight(VolleyUiUtil.HOME_FIND_GAME_TEXT_WEIGHT)
                    .padding(
                        top = VolleyDimens.DIMEN_12.dp,
                        start = VolleyDimens.DIMEN_12.dp,
                        end = VolleyDimens.DIMEN_6.dp,
                        bottom = VolleyDimens.DIMEN_4.dp
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
                        .padding(start = VolleyDimens.DIMEN_36.dp, end = VolleyDimens.DIMEN_24.dp)
                        .height(VolleyDimens.DIMEN_40.dp)
                ) {
                    Image(
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        painter = painterResource(R.drawable.arrow_horizontal_up),
                    )
                }
            }

            // GameAvailableBlock here
            GamesAvailableBlock(
                gamesCount = gamesCount,
                modifier = Modifier.weight(VolleyUiUtil.HOME_FIND_GAME_COUNT_WEIGHT)
            )
        }
    }
}

@Composable
private fun GamesAvailableBlock(
    modifier: Modifier = Modifier,
    gamesCount: Int = 0,
) {
    val digitsStringValuesList = gamesCount.toString().chunked(1)
    val topGapForDigits = when(digitsStringValuesList.size) {
        3 -> VolleyDimens.DIMEN_6
        4 -> VolleyDimens.DIMEN_12
        else -> 0
    }

    Box(
        modifier = modifier
            .height(VolleyDimens.DIMEN_100.dp)
            .background(
                color = VolleyColor.White,
                shape = RoundedCornerShape(VolleyDimens.DIMEN_28.dp)
            )
            .padding(
            horizontal = VolleyDimens.DIMEN_20.dp,
            vertical = VolleyDimens.DIMEN_8.dp
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth().weight(1f)
                    .padding(
                        top = topGapForDigits.dp,
                        bottom = (VolleyDimens.DIMEN_12 + topGapForDigits).dp
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
        Spacer(Modifier.width(VolleyDimens.DIMEN_8.dp))
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
    val shape = remember { RoundedCornerShape(VolleyDimens.DIMEN_32.dp) }
    val insidePaddings = remember {
        PaddingValues(
            top = VolleyDimens.DIMEN_20.dp,
            start = VolleyDimens.DIMEN_20.dp
        )
    }

    Box(
        modifier = modifier
            .fillMaxWidth(0.5f)
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
                .rotate(VolleyDimens.ROTATION_8),
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
            top = VolleyDimens.DIMEN_28.dp,
            start = VolleyDimens.DIMEN_30.dp
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
                .rotate(VolleyDimens.ROTATION_8),
            text = stringResource(R.string.donate),
            color = VolleyColor.White
        )
    }
}

@Preview
@Composable
private fun PreviewFindGameButton() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
            .background(VolleyColor.TurquoiseDark)
    ) {
        Column {
            FindGameButton(
                gamesCount = 0,
                onClick = {}
            )
            Spacer(Modifier.height(VolleyDimens.DIMEN_20.dp))
            FindGameButton(
                gamesCount = 22,
                onClick = {}
            )
            Spacer(Modifier.height(VolleyDimens.DIMEN_20.dp))
            FindGameButton(
                gamesCount = 222,
                onClick = {}
            )
            Spacer(Modifier.height(VolleyDimens.DIMEN_20.dp))
            FindGameButton(
                gamesCount = 2222,
                onClick = {}
            )
        }
    }
}

//@Preview
@Composable
private fun PreviewHomeScreen() {
    VolleyContainersRootTransparent.Root {
        Box(
            contentAlignment = Alignment.TopStart,
            modifier = Modifier
                .fillMaxSize()
                .background(VolleyColor.TurquoiseDark)
        ) {
            HomeScreen(
                state = HomeScreenState(),
                effect = null,
                navigateAction = {},
                eventCallback = {}
            )
        }
    }
}
