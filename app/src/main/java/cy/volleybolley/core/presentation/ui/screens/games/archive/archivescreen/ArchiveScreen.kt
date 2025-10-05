package cy.volleybolley.core.presentation.ui.screens.games.archive.archivescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
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
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.model.VolleyTypography
import cy.volleybolley.core.presentation.ui.navigation.NavMap
import cy.volleybolley.core.presentation.ui.screens.games.archive.archivescreen.effect.ArchiveEffect
import cy.volleybolley.core.presentation.ui.screens.games.archive.archivescreen.event.ArchiveEvent
import cy.volleybolley.core.presentation.ui.screens.games.archive.archivescreen.model.ArchiveState
import cy.volleybolley.core.presentation.ui.screens.games.archive.archivescreen.viewmodel.ArchiveViewModel
import cy.volleybolley.core.presentation.ui.screens.games.archive.datamodel.Game
import cy.volleybolley.core.presentation.ui.screens.games.archive.datamodel.Host
import cy.volleybolley.core.presentation.ui.screens.games.archive.util.DataTimeRangeFormatter
import cy.volleybolley.core.presentation.ui.screens.games.archive.util.openMap
import cy.volleybolley.courts.domain.model.Location

@Composable
fun ArchiveScreen(
    navController: NavHostController,
    viewModel: ArchiveViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val effect by viewModel.uiEffect.collectAsStateWithLifecycle(null)

    ArchiveScreen(
        state = state,
        effect = effect,
        onBackClick = { navController.popBackStack() },
        navigateAction = { route ->
            navController.navigate(route)
        },
        eventCallback = { event ->
            viewModel.obtainEvent(event)
        }
    )
}

@Composable
private fun ArchiveScreen(
    state: ArchiveState,
    effect: ArchiveEffect?,
    onBackClick: () -> Unit,
    navigateAction: (NavMap) -> Unit,
    eventCallback: (ArchiveEvent) -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(effect) {
        effect?.let {
            when (it) {
                is ArchiveEffect.Navigate -> navigateAction(it.route)
                ArchiveEffect.NavigateBack -> onBackClick
                is ArchiveEffect.OpenMap -> context.openMap(it.location)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = VolleyDimens.DIMEN_8.dp
            )
    ) {

        if (state.emptyArchive) {
            ArchiveNotFoundPlaceHolder(
                onBackClick = onBackClick,
                onButtonClick = { eventCallback(ArchiveEvent.ClickCreateGame) }
            )
        } else {
            ArchiveLazyColumn(
                games = state.games,
                onBackClick = onBackClick,
                onButtonClick = { game -> eventCallback(ArchiveEvent.ClickDetails(game)) },
                onMapClick = { location -> eventCallback(ArchiveEvent.ClickMap(location)) }
            )
        }
    }
}

@Composable
private fun ArchiveLazyColumn(
    games: List<Game>,
    onBackClick: () -> Unit,
    onButtonClick: (Game) -> Unit,
    onMapClick: (Location) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(VolleyDimens.DIMEN_8.dp)
    ) {
        itemsIndexed(
            items = games
        ) { index, game ->
            ArchiveCard(
                game = game,
                onBackClick = onBackClick,
                onButtonClick = onButtonClick,
                onMapClick = onMapClick,
                showHeader = index == 0
            )
        }
    }
}

@Composable
private fun ArchiveCard(
    game: Game,
    onBackClick: () -> Unit,
    onButtonClick: (Game) -> Unit,
    onMapClick: (Location) -> Unit,
    showHeader: Boolean
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

            if (showHeader) {
                ArchiveHeader(onBackClick)
            }

            HostInfoBlock(
                host = game.host
            )

            HorizontalDivider(
                thickness = VolleyDimens.DIMEN_1.dp,
                color = VolleyColor.White.copy(alpha = 0.25f)
            )

            GameInfoBlock(
                game = game,
                onMapClick = onMapClick
            )

            DetailsButton(
                onButtonClick = { onButtonClick(game) }
            )
        }
    }
}

@Composable
private fun HostInfoBlock(host: Host) {
    Column {
        VolleyText.BodyBold(
            text = stringResource(R.string.game_host),
            color = VolleyColor.White,
            modifier = Modifier.align(Alignment.Start)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = VolleyDimens.DIMEN_8.dp
                ),
            verticalAlignment = Alignment.CenterVertically

        ) {
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
                    text = host.level,
                    color = VolleyColor.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
private fun GameInfoBlock(
    game: Game,
    onMapClick: (Location) -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = VolleyDimens.DIMEN_8.dp)
        ) {
            VolleyText.BodyBold(
                text = stringResource(R.string.completed),
                color = VolleyColor.White,
                modifier = Modifier.padding(end = VolleyDimens.DIMEN_4.dp)
            )

            val (dateText, timeText) = remember(game.startTime, game.endTime) {
                DataTimeRangeFormatter.format(game.startTime, game.endTime)
            }

            VolleyText.BodyRegular(
                text = "$dateText, $timeText",
                color = VolleyColor.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = VolleyDimens.DIMEN_8.dp)
        ) {
            val label = stringResource(R.string.place_archive)
            val text = buildAnnotatedString {
                append("$label ")
                addStyle(SpanStyle(fontWeight = FontWeight.Bold), 0, label.length)
                append(game.courtLocation.courtName)
            }
            Text(
                text = text,
                color = VolleyColor.White,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = VolleyTypography.BodyRegular,
                modifier = Modifier
                    .weight(1f)
            )

            VolleyButton.ActiveButtonMap(
                text = stringResource(R.string.map),
                onClick = {
                    onMapClick(game.courtLocation)
                },
                paddingValues = PaddingValues(VolleyDimens.DIMEN_12.dp, VolleyDimens.DIMEN_8.dp),
                cornerRadius = VolleyDimens.DIMEN_12.dp
            )
        }

        VolleyContainersRootTransparent.TransparentContainer(
            cornerRadius = VolleyDimens.DIMEN_16,
            modifier = Modifier.fillMaxWidth()
        ) {
            VolleyText.BodyRegular(
                text = game.message,
                color = VolleyColor.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(VolleyDimens.DIMEN_16.dp)
            )
        }
    }
}

@Composable
private fun DetailsButton(onButtonClick: () -> Unit) {
    VolleyButton.OutlinedActiveButton(
        text = stringResource(R.string.details),
        onClick = onButtonClick,
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
private fun ArchiveNotFoundPlaceHolder(
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
            ArchiveHeader(onBackClick)
            PlaceholderMessage()
            CreateGameButton(onButtonClick)
        }
    }
}

@Composable
private fun ArchiveHeader(
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
            text = stringResource(R.string.archive),
            color = VolleyColor.White,
            modifier = Modifier.align(Alignment.Center)
        )

    }
}

@Composable
private fun PlaceholderMessage() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
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
            text = stringResource(R.string.No_archived_games_found),
            color = VolleyColor.White,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun CreateGameButton(onClick: () -> Unit) {
    VolleyButton.ActiveButton(
        text = stringResource(R.string.create_a_game),
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ArchiveScreenPreview() {
    VolleyContainersRootTransparent.Root {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(VolleyColor.TurquoiseDark)
        ) {
            ArchiveScreen(
                state = ArchiveState(emptyArchive = false),
                effect = null,
                onBackClick = {},
                navigateAction = {},
                eventCallback = {}
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ArchiveScreenPlaceholderPreview() {
    VolleyContainersRootTransparent.Root {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(VolleyColor.TurquoiseDark)
        ) {
            ArchiveScreen(
                state = ArchiveState(emptyArchive = true),
                effect = null,
                onBackClick = {},
                navigateAction = {},
                eventCallback = {}
            )
        }
    }
}
