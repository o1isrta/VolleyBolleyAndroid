package cy.volleybolley.core.presentation.ui.screens.games.archive.pasttourneyscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
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
import cy.volleybolley.core.presentation.ui.component.VolleyTopBar
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.navigation.NavMap
import cy.volleybolley.core.presentation.ui.screens.games.archive.datamodel.Host
import cy.volleybolley.core.presentation.ui.screens.games.archive.datamodel.ShortTeam
import cy.volleybolley.core.presentation.ui.screens.games.archive.datamodel.Tourney
import cy.volleybolley.core.presentation.ui.screens.games.archive.pasttourneyscreen.effect.PastTourneyEffect
import cy.volleybolley.core.presentation.ui.screens.games.archive.pasttourneyscreen.event.PastTourneyEvent
import cy.volleybolley.core.presentation.ui.screens.games.archive.pasttourneyscreen.model.PastTourneyState
import cy.volleybolley.core.presentation.ui.screens.games.archive.pasttourneyscreen.viewmodel.PastTourneyViewModel
import cy.volleybolley.core.presentation.ui.screens.games.archive.util.DataTimeRangeFormatter
import cy.volleybolley.core.presentation.ui.screens.games.archive.util.openMap
import cy.volleybolley.courts.domain.model.Location
import cy.volleybolley.ui.theme.VolleybolleyTheme

@Composable
fun PastTourneyScreen(
    navController: NavHostController,
    viewModel: PastTourneyViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val effect by viewModel.uiEffect.collectAsStateWithLifecycle(null)

    PastTourneyScreen(
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
private fun PastTourneyScreen(
    state: PastTourneyState,
    effect: PastTourneyEffect?,
    onBackClick: () -> Unit,
    navigateAction: (NavMap) -> Unit,
    eventCallback: (PastTourneyEvent) -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(effect) {
        effect?.let {
            when (it) {
                PastTourneyEffect.NavigateBack -> onBackClick
                is PastTourneyEffect.OpenMap -> context.openMap(it.location)
                is PastTourneyEffect.Navigate -> navigateAction(it.route)
            }
        }
    }

    Render(
        state = state,
        onBackClick = onBackClick,
        eventCallback = eventCallback,
        modifier = Modifier.padding(horizontal = VolleyDimens.DIMEN_8.dp)
    )
}

@Stable
@Composable
private fun Render(
    state: PastTourneyState,
    onBackClick: () -> Unit,
    eventCallback: (PastTourneyEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        when (state) {
            PastTourneyState.Loading -> ShowLoader()

            PastTourneyState.Error -> ShowErrorPlaceholder(
                onBackClick = onBackClick,
                onButtonClick = { eventCallback(PastTourneyEvent.Refresh) }
            )

            is PastTourneyState.Content -> ShowPastTourneyDetails(
                tourney = state.tourney,
                onBackClick = onBackClick,
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
    tourney: Tourney,
    onBackClick: () -> Unit,
    onMapClick: () -> Unit,
    onJoinedPlayersClick: () -> Unit
) {
    VolleyContainersRootTransparent.TransparentContainer(
        cornerRadius = VolleyDimens.DIMEN_32
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(VolleyDimens.DIMEN_16.dp),
            modifier = modifier
                .fillMaxWidth()
                .padding(VolleyDimens.DIMEN_20.dp)
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
                paymentType = tourney.paymentType,
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
    teams: List<ShortTeam>?,
    onButtonClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(VolleyDimens.DIMEN_12.dp),
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
                horizontal = VolleyDimens.DIMEN_16.dp,
                vertical = VolleyDimens.DIMEN_14.dp
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
                        paymentAccount ?: ""
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
        verticalArrangement = Arrangement.spacedBy(VolleyDimens.DIMEN_16.dp),
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
            text = stringResource(R.string.tourney_host),
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
                    text = host.level,
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
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onButtonClick: () -> Unit,
) {
    VolleyContainersRootTransparent.TransparentContainer(
        cornerRadius = VolleyDimens.DIMEN_32
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(VolleyDimens.DIMEN_16.dp),
            modifier = modifier
                .fillMaxWidth()
                .padding(VolleyDimens.DIMEN_20.dp)
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
        CircularProgressIndicator()
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
                effect = null,
                onBackClick = {},
                eventCallback = {},
                navigateAction = {}
            )
        }
    }
}
