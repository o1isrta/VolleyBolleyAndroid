package cy.volleybolley.core.presentation.ui.screens.games.upcominggames

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.VolleySimpleComponent.TitleWithBackArrow
import cy.volleybolley.core.presentation.ui.component.VolleyButton
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.screens.games.upcominggames.dataholder.TournamentDetailsDataHolder
import cy.volleybolley.games.domain.model.event.tournament.TournamentDetails
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun UpcomingTourneyDetailsScreen(
    paddingFromSystemUi: PaddingValues,
    tournamentDetails: TournamentDetails?,
    onNavigateToJoinedPlayers: (String) -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: UpcomingTourneyDetailsViewModel = koinViewModel { parametersOf(tournamentDetails) }
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val effect by viewModel.uiEffect.collectAsStateWithLifecycle(null)
    val dataHolder: TournamentDetailsDataHolder = org.koin.compose.koinInject()

    LaunchedEffect(effect) {
        when (val currentEffect = effect) {
            is UpcomingTourneyDetailsEffect.NavigateBack -> onNavigateBack()
            is UpcomingTourneyDetailsEffect.NavigateToJoinedPlayers -> {
                val key = dataHolder.put(currentEffect.tournamentDetails)
                onNavigateToJoinedPlayers(key)
            }
            null -> {}
        }
    }

    UpcomingTourneyDetailsScreen(
        state = state,
        paddingFromSystemUi = paddingFromSystemUi,
        eventCallback = { viewModel.obtainEvent(it) }
    )
}

@Stable
@Composable
private fun UpcomingTourneyDetailsScreen(
    state: UpcomingTourneyDetailsState,
    paddingFromSystemUi: PaddingValues,
    eventCallback: (UpcomingTourneyDetailsEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingFromSystemUi)
    ) {
        VolleyContainersRootTransparent.TransparentContainer(modifier = Modifier.padding(8.dp)) {
            Column(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                TitleWithBackArrow(
                    title = stringResource(R.string.details),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 20.dp),
                    onBackClick = { eventCallback(UpcomingTourneyDetailsEvent.OnBackClicked) }
                )

                VolleyText.BodyRegular(
                    text = "Upcoming tourney details screen - TODO",
                    modifier = Modifier.padding(top = 20.dp),
                    color = VolleyColor.White
                )

                VolleyButton.ActiveButton(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth(),
                    text = stringResource(R.string.joined_players),
                    onClick = { eventCallback(UpcomingTourneyDetailsEvent.OnViewPlayersClicked) }
                )
            }
        }
    }
}

@Preview
@Composable
private fun UpcomingTourneyDetailsScreenPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VolleyColor.TurquoiseDark)
    ) {
        UpcomingTourneyDetailsScreen(
            state = UpcomingTourneyDetailsState(),
            paddingFromSystemUi = PaddingValues(0.dp),
            eventCallback = {}
        )
    }
}
