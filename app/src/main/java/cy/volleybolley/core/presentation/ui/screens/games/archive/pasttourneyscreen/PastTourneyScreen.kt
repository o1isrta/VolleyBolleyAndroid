package cy.volleybolley.core.presentation.ui.screens.games.archive.pasttourneyscreen

import androidx.compose.foundation.Image
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
import androidx.navigation.NavHostController
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.component.VolleyAvatar
import cy.volleybolley.core.presentation.ui.component.VolleyButton
import cy.volleybolley.core.presentation.ui.component.VolleyProgress
import cy.volleybolley.core.presentation.ui.component.VolleyTopBar
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.screens.games.archive.pasttourneyscreen.effect.PastTourneyEffect
import cy.volleybolley.core.presentation.ui.screens.games.archive.pasttourneyscreen.event.PastTourneyEvent
import cy.volleybolley.core.presentation.ui.screens.games.archive.pasttourneyscreen.model.PastTourneyState
import cy.volleybolley.core.presentation.ui.screens.games.archive.pasttourneyscreen.viewmodel.PastTourneyViewModel
import cy.volleybolley.core.presentation.ui.screens.games.archive.util.DataTimeRangeFormatter
import cy.volleybolley.core.presentation.ui.screens.games.archive.util.openMap
import cy.volleybolley.courts.domain.model.Location
import cy.volleybolley.games.domain.model.entity.Host
import cy.volleybolley.games.domain.model.entity.Team
import cy.volleybolley.games.domain.model.event.tournament.TournamentDetails
import cy.volleybolley.ui.theme.VolleybolleyTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PastTourneyScreen(
    navController: NavHostController,
    viewModel: PastTourneyViewModel = koinViewModel(),
    paddingFromSystemUi: PaddingValues
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val effect by viewModel.uiEffect.collectAsStateWithLifecycle(null)
    val context = LocalContext.current

    LaunchedEffect(effect) {
        when (val currentEffect = effect) {
            is PastTourneyEffect.NavigateBack -> navController.popBackStack()
            is PastTourneyEffect.OpenMap -> context.openMap(currentEffect.location)
            is PastTourneyEffect.Navigate -> navController.navigate(currentEffect.route)
            null -> {}
        }
    }

    PastTourneyScreen(
        state = state,
        paddingFromSystemUi = paddingFromSystemUi,
        eventCallback = { viewModel.obtainEvent(it) }
    )
}

@Stable
@Composable
private fun PastTourneyScreen(
    state: PastTourneyState,
    paddingFromSystemUi: PaddingValues,
    eventCallback: (PastTourneyEvent) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingFromSystemUi)
            .padding(horizontal = 8.dp)
            .verticalScroll(rememberScrollState())
    ) {
        when (state) {
            PastTourneyState.Loading -> ShowLoader()

            PastTourneyState.Error -> ShowErrorPlaceholder(
                onBackClick = { eventCallback(PastTourneyEvent.OnBackClick) },
                onButtonClick = { eventCallback(PastTourneyEvent.Refresh) }
            )

            is PastTourneyState.Content -> ShowPastTourneyDetails(
                tourney = state.tourney,
                onBackClick = { eventCallback(PastTourneyEvent.OnBackClick) },
                onMapClick = { eventCallback(PastTourneyEvent.OnMapClick(state.tourney.courtLocation)) },
                onJoinedPlayersClick = { eventCallback(PastTourneyEvent.OnJoinedPlayersClick(state.tourney.teams)) }
            )
        }
    }
}

@Stable
@Composable
private fun ShowPastTourneyDetails(
    modifier: Modifier = Modifier,
    tourney: TournamentDetails,
    onBackClick: () -> Unit,
    onMapClick: () -> Unit,
    onJoinedPlayersClick: () -> Unit
) {
    VolleyContainersRootTransparent.TransparentContainer(
        cornerRadius = 32
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            VolleyTopBar.TopBarWithBackButton(
                title = stringResource(R.string.past_tourney),
                modifier = Modifier.fillMaxWidth()
            ) { onBackClick }

            HostInfoBlock(tourney.host, tourney.message)
            DividerGlass()
            AboutTourneyBlock(
                courtLocation = tourney.courtLocation,
                startTime = tourney.startTime,
                endTime = tourney.endTime,
                level = tourney.levels[0],
                gender = tourney.gender
            ) { onMapClick }
            DividerGlass()
            PaymentBlock(
                paymentType = tourney.paymentType.name,
                paymentAccount = tourney.paymentAccount,
                currencyType = tourney.currencyType,
                pricePerPerson = tourney.pricePerPerson
            )
            DividerGlass()
            JoinedPlayersBlock(
                isIndividual = tourney.isIndividual,
                teams = tourney.teams
            ) { onJoinedPlayersClick }
        }
    }
}

@Stable
@Composable
private fun JoinedPlayersBlock(
    isIndividual: Boolean,
    teams: List<Team>?,
    onButtonClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        VolleyText.TitleMedium(
            text = stringResource(R.string.joined_players_loading),
            color = VolleyColor.White,
            modifier = Modifier.align(Alignment.Start)
        )

        VolleyButton.CheckedGradientButtonRightImage(
            text = if (isIndividual) {
                stringResource(R.string.players)
            } else {
                stringResource(
                    R.string.team_number,
                    teams?.size!!
                )
            },
            isChecked = false,
            paddingValues = PaddingValues(
                horizontal = 16.dp,
                vertical = 14.dp
            ),
            onClick = onButtonClick
        )
    }
}

@Stable
@Composable
private fun PaymentBlock(
    paymentType: String,
    paymentAccount: String?,
    currencyType: String,
    pricePerPerson: String
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
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
                        paymentAccount ?: ""
                    )
                },
                color = VolleyColor.White
            )
        }

        VolleyContainersRootTransparent.TransparentContainer(
            cornerRadius = 16
        ) {
            VolleyText.BodyRegular(
                text = stringResource(R.string.per_person) + " $pricePerPerson$currencyType",
                color = VolleyColor.White,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
private fun AboutTourneyBlock(
    courtLocation: Location,
    startTime: String,
    endTime: String,
    level: String,
    gender: String,
    onMapClick: (Location) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        VolleyText.TitleMedium(
            text = stringResource(R.string.about_tourney),
            color = VolleyColor.White,
            modifier = Modifier.align(Alignment.Start)
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(R.drawable.ic_nav_yellow_arrow),
                contentDescription = null,
                tint = VolleyColor.OrangeHard,
                modifier = Modifier.size(16.dp)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                VolleyText.BodyBold(courtLocation.courtName, color = VolleyColor.White)
                VolleyText.BodyLight(courtLocation.locationName, color = VolleyColor.White)
            }
            VolleyButton.ActiveButtonMap(
                text = stringResource(R.string.map),
                onClick = {
                    onMapClick(courtLocation)
                },
                paddingValues = PaddingValues(16.dp, 12.dp)
            )

        }

        Row {
            VolleyText.BodyBold(
                text = stringResource(R.string.`when`),
                color = VolleyColor.White,
                modifier = Modifier.padding(end = 4.dp)
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
                modifier = Modifier.padding(end = 4.dp)
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
                modifier = Modifier.padding(end = 4.dp)
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
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        VolleyText.TitleMedium(
            text = stringResource(R.string.tourney_host),
            color = VolleyColor.White,
            modifier = Modifier.align(Alignment.Start)
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            VolleyAvatar.CircularAvatar(
                host.avatar,
                40.dp
            )

            VolleyText.BodyRegular(
                text = host.name,
                color = VolleyColor.White,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            )

            Box(
                modifier = Modifier
                    .size(32.dp, 20.dp)
                    .clip(RoundedCornerShape(8.dp))
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
            cornerRadius = 16
        ) {
            VolleyText.BodyRegular(
                text = message,
                color = VolleyColor.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}

@Stable
@Composable
private fun DividerGlass() {
    HorizontalDivider(
        thickness = 1.dp,
        color = VolleyColor.White.copy(alpha = 0.25f)
    )
}

@Stable
@Composable
private fun ShowErrorPlaceholder(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onButtonClick: () -> Unit,
) {
    VolleyContainersRootTransparent.TransparentContainer(
        cornerRadius = 32
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            VolleyTopBar.TopBarWithBackButton(
                title = stringResource(R.string.past_tourney),
                modifier = Modifier.fillMaxWidth()
            ) { onBackClick }
            PlaceholderMessage()
            RefreshButton(onButtonClick)
        }
    }
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
                    top = 8.dp,
                    bottom = 16.dp
                )
                .size(160.dp)
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
private fun PastTourneyScreenPreview() {
    VolleybolleyTheme {
        Box(
            Modifier
                .fillMaxSize()
                .background(VolleyColor.TurquoiseDark)
        ) {
            PastTourneyScreen(
                state = PastTourneyState.Content(),
                paddingFromSystemUi = PaddingValues(0.dp),
                eventCallback = {}
            )
        }
    }
}
