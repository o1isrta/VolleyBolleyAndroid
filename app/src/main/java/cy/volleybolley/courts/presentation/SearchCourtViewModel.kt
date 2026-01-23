package cy.volleybolley.courts.presentation

import com.google.android.gms.maps.model.LatLng
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.courts.domain.api.CourtsUseCase
import cy.volleybolley.courts.presentation.model.CourtUi
import cy.volleybolley.courts.presentation.model.CourtUiMapper.toCourtUI
import cy.volleybolley.courts.presentation.model.CourtUiMapper.updateDistance
import cy.volleybolley.games.domain.model.event.EventType
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class SearchCourtViewModel @Inject constructor(
    private val courtsUseCase: CourtsUseCase,
    private val eventType: EventType,
) : BaseViewModel<SearchCourtState, SearchCourtEvent, SearchCourtEffect>(SearchCourtState()) {

    override val tag: String = "SearchCourtViewModel"

    override fun obtainEvent(event: SearchCourtEvent) {
        when (event) {
            SearchCourtEvent.LoadSearchCourt -> loadCourts()
            SearchCourtEvent.OnBackFromSearchCourtListScreen ->
                sendUiEffect(SearchCourtEffect.NavigateBack)

            is SearchCourtEvent.ClickOnCourtMarker ->
                clickOnCourtMarker(event.court)

            is SearchCourtEvent.ToggleCourtDetailsFromMap ->
                toggleCourtMapDetails(event.court)

            is SearchCourtEvent.ClickOnCourtFromList ->
                clickOnCourtFromList(event.court)

            is SearchCourtEvent.ClickOnChooseSearchCourt ->
                clickOnChooseCourt(event.court)

            SearchCourtEvent.ClickOnMap ->
                clickOnMap()

            is SearchCourtEvent.UpdateUserLocation ->
                updateUserLocation(event.latLng)

            SearchCourtEvent.DeniedUserLocation ->
                deniedUserLocation()
        }
    }

    init {
        loadCourts()
    }

    private fun loadCourts() {
        uiStateMutable.update { it.copy(isLoading = true) }

        launchSafe(getErrorLogMessage = { "Failed to load courts: ${it.message}" }, block = {
            when (val result = courtsUseCase.getCourts()) {
                is VolleyResult.Success -> {
                    val userLocation = uiState.value.userLocation
                    val uiCourts = result.data.map { it.toCourtUI(userLocation) }
                    uiStateMutable.update { it.copy(courts = uiCourts, isLoading = false) }
                }

                is VolleyResult.Failure -> {
                    uiStateMutable.update { it.copy(isLoading = false) }
                    sendUiEffect(SearchCourtEffect.ShowError("Failed to load courts: ${result.error}"))
                }
            }
        }, onError = { error ->
            uiStateMutable.update { it.copy(isLoading = false) }
            sendUiEffect(SearchCourtEffect.ShowError("Network error: ${error.message}"))
        })
    }

    private fun updateUserLocation(latLng: LatLng) {
        uiStateMutable.update { it.copy(userLocation = latLng) }
        updateCourtsWithDistance(latLng)
    }

    private fun deniedUserLocation() {
        uiStateMutable.update { it.copy(userLocation = null) }
        updateCourtsWithDistance(null)
    }

    private fun clickOnCourtMarker(court: CourtUi) {
        uiStateMutable.update { state ->
            if (state.selectedCourt == court) {
                state
            } else {
                state.copy(
                    selectedCourt = court,
                    showDetails = false
                )
            }
        }
    }

    private fun clickOnCourtFromList(court: CourtUi) {
        uiStateMutable.update { state ->
            when {
                state.selectedCourt != court ->
                    state.copy(
                        selectedCourt = court,
                        showDetails = true
                    )

                else ->
                    state.copy(showDetails = !state.showDetails, selectedCourt = null)
            }
        }
    }

    private fun toggleCourtMapDetails(court: CourtUi) {
        uiStateMutable.update { state ->
            if (state.selectedCourt != court) return@update state
            state.copy(showDetails = !state.showDetails)
        }
    }

    private fun clickOnChooseCourt(court: CourtUi) {
        sendUiEffect(
            SearchCourtEffect.NavigateToGameCreation(
                selectedCourt = court, eventType = eventType
            )
        )
    }

    private fun clickOnMap() {
        uiStateMutable.update {
            it.copy(selectedCourt = null, showDetails = false)
        }
    }

    private fun updateCourtsWithDistance(userLocation: LatLng?) {
        uiStateMutable.update { state ->
            val updatedCourts = state.courts.map { it.updateDistance(userLocation) }
            state.copy(courts = updatedCourts)
        }
    }
}
