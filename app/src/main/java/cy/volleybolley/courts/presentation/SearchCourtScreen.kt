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
import cy.volleybolley.core.presentation.ui.screens.courts.CourtsComponents.CourtMapListSwitcherScreen
import cy.volleybolley.games.domain.model.event.EventType

@Composable
fun SearchCourtScreen(
    paddingFromSystemUi: PaddingValues,
    onNavigateToGameCreation: (EventType) -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: SearchCourtViewModel,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val effect by viewModel.uiEffect.collectAsStateWithLifecycle(null)
    val context = LocalContext.current
    var isMapSelected by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(effect) {
        when (val currentEffect = effect) {
            is SearchCourtEffect.ShowError -> {
                Toast.makeText(context, currentEffect.message, Toast.LENGTH_SHORT).show()
            }

            is SearchCourtEffect.NavigateToGameCreation -> onNavigateToGameCreation(currentEffect.eventType)

            is SearchCourtEffect.NavigateBack -> onNavigateBack()

            null -> {}
        }
    }

    LaunchedEffect(Unit) {
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
        onBackNavigationRequested = {
            onEvent(SearchCourtEvent.OnBackFromSearchCourtListScreen)
        },
        onMapCourtClick = { court ->
            onEvent(SearchCourtEvent.ClickOnCourtMarker(court))
        },
        onMapCourtDetailsClick = { court ->
            onEvent(SearchCourtEvent.ToggleCourtDetailsFromMap(court))
        },
        onMapClick = {
            onEvent(SearchCourtEvent.ClickOnMap)
        },
        onListCourtClick = { court ->
            onEvent(SearchCourtEvent.ClickOnCourtFromList(court))
        },
        onCourtChoose = { court ->
            onEvent(SearchCourtEvent.ClickOnChooseSearchCourt(court))
        },
        onUserLocationUpdate = { latLng ->
            onEvent(SearchCourtEvent.UpdateUserLocation(latLng))
        },
        onUserLocationDenied = {
            onEvent(SearchCourtEvent.DeniedUserLocation)
        }
    )
}
