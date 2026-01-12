package cy.volleybolley.courts.presentation

import com.google.android.gms.maps.model.LatLng
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.courts.domain.api.CourtsUseCase
import cy.volleybolley.courts.domain.model.Court
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
            is SearchCourtEvent.LoadSearchCourt -> loadCourts()
            is SearchCourtEvent.OnBackFromSearchCourtListScreen -> sendUiEffect(SearchCourtEffect.NavigateBack)
            is SearchCourtEvent.ClickOnSearchCourtMarker -> clickOnCourtMarker(event.court)
            is SearchCourtEvent.ClickOnSearchCourtDetails -> clickOnCourtDetails(event.court)
            is SearchCourtEvent.ClickOnChooseSearchCourt -> clickOnChooseCourt(event.court)
            is SearchCourtEvent.ClickOnMap -> clickOnMap()
            is SearchCourtEvent.UpdateUserLocation -> updateUserLocation(event.latLng)
            is SearchCourtEvent.DeniedUserLocation -> deniedUserLocation()
        }
    }

    init {
        loadCourts()
    }
    private fun loadCourts() {
        uiStateMutable.update { it.copy(isLoading = true) }

        launchSafe(
            getErrorLogMessage = { "Failed to load courts: ${it.message}" },
            block = {
                when (val result = courtsUseCase.getCourts()) {
                    is VolleyResult.Success -> uiStateMutable.update {
                        it.copy(courts = result.data, isLoading = false)
                    }

                    is VolleyResult.Failure -> {
                        uiStateMutable.update { it.copy(isLoading = false) }
                        sendUiEffect(SearchCourtEffect.ShowError("Failed to load courts: ${result.error}"))
                    }
                }
            },
            onError = { error ->
                uiStateMutable.update { it.copy(isLoading = false) }
                sendUiEffect(SearchCourtEffect.ShowError("Network error: ${error.message}"))
            }
        )
    }

    private fun updateUserLocation(latLng: LatLng) {
        uiStateMutable.update { it.copy(userLocation = latLng) }
    }

    private fun deniedUserLocation() {
        uiStateMutable.update { it.copy(userLocation = null) }
    }

    private fun clickOnCourtMarker(court: Court) {
        uiStateMutable.update { state ->
            if (state.selectedCourt == court) {
                state.copy(selectedCourt = null, showDetails = false)
            } else {
                state.copy(selectedCourt = court, showDetails = true)
            }
        }
    }

    private fun clickOnCourtDetails(court: Court) {
        uiStateMutable.update {
            it.copy(selectedCourt = court, showDetails = true)
        }
    }

    private fun clickOnChooseCourt(court: Court) {
        sendUiEffect(
            SearchCourtEffect.NavigateToGameCreation(
                selectedCourt = court,
                eventType = eventType
            )
        )
    }

    private fun clickOnMap() {
        uiStateMutable.update {
            it.copy(selectedCourt = null, showDetails = false)
        }
    }
}
