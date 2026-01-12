package cy.volleybolley.courts.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import cy.volleybolley.core.presentation.ui.navigation.BasicGameSetupRoute
import cy.volleybolley.core.presentation.ui.navigation.BasicTourneySetupRoute
import cy.volleybolley.core.presentation.ui.screens.courts.CourtsComponents.CourtMapListSwitcherScreen
import cy.volleybolley.games.domain.model.event.EventType
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SearchCourtScreen(
    navController: NavHostController,
    paddingFromSystemUi: PaddingValues,
    viewModel: SearchCourtViewModel,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var isMapSelected by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collectLatest { effect ->
            when (effect) {
                is SearchCourtEffect.ShowError -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }

                is SearchCourtEffect.NavigateToGameCreation -> {
                    when (effect.eventType) {
                        EventType.GAME -> {
                            navController.navigate(BasicGameSetupRoute)
                        }

                        EventType.TOURNAMENT -> {
                            navController.navigate(BasicTourneySetupRoute)
                        }
                    }
                }

                is SearchCourtEffect.NavigateBack -> {
                    navController.popBackStack()
                }

                null -> {}
            }
        }
        viewModel.obtainEvent(SearchCourtEvent.LoadSearchCourt)
    }

    CourtListContent(
        modifier = Modifier.padding(paddingFromSystemUi),
        isMapSelected = isMapSelected,
        onTabSelected = { isMapSelected = it },
        state = state,
        onEvent = { event -> viewModel.obtainEvent(event) }
    )
}

@Composable
private fun CourtListContent(
    modifier: Modifier,
    isMapSelected: Boolean,
    onTabSelected: (Boolean) -> Unit,
    state: SearchCourtState,
    onEvent: (SearchCourtEvent) -> Unit,
) {
    CourtMapListSwitcherScreen(
        modifier = modifier,
        courts = state.courts,
        selectedCourt = state.selectedCourt,
        userLocation = state.userLocation,
        showDetails = state.showDetails,
        isLoading = state.isLoading,
        error = state.error,
        isMapSelected = isMapSelected,
        onTabSelected = onTabSelected,
        onBackNavigationRequested = { onEvent(SearchCourtEvent.OnBackFromSearchCourtListScreen) },
        onCourtClick = { court -> onEvent(SearchCourtEvent.ClickOnSearchCourtMarker(court)) },
        onCourtChoose = { court -> onEvent(SearchCourtEvent.ClickOnChooseSearchCourt(court)) },
        onMapClick = { onEvent(SearchCourtEvent.ClickOnMap) },
        onCourtDetailsClick = { court -> onEvent(SearchCourtEvent.ClickOnSearchCourtDetails(court)) },
        onUserLocationUpdate = { latLng -> onEvent(SearchCourtEvent.UpdateUserLocation(latLng)) },
        onUserLocationDenied = { onEvent(SearchCourtEvent.DeniedUserLocation) },
    )
}
