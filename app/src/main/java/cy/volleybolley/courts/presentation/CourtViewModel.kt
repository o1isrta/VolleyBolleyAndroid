package cy.volleybolley.courts.presentation

import com.google.android.gms.maps.model.LatLng
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.courts.domain.api.CourtsUseCase
import cy.volleybolley.courts.domain.model.Court
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class CourtViewModel @Inject constructor(
    private val courtsUseCase: CourtsUseCase
) : BaseViewModel<CourtState, CourtEvent, CourtEffect>(CourtState()) {

    override val tag: String = "CourtViewModel"

    override fun obtainEvent(event: CourtEvent) {
        when (event) {
            is CourtEvent.LoadCourts -> loadCourts()
            is CourtEvent.OnBackFromCourtListScreen -> sendUiEffect(CourtEffect.NavigateBack)
            is CourtEvent.ClickOnCourtMarker -> clickOnCourtMarker(event.court)
            is CourtEvent.ClickOnCourtDetails -> clickOnCourtDetails(event.court)
            is CourtEvent.ClickOnChooseCourt -> clickOnChooseCourt(event.court)
            is CourtEvent.ClickOnMap -> clickOnMap()
            is CourtEvent.UpdateUserLocation -> updateUserLocation(event.latLng)
            is CourtEvent.DeniedUserLocation -> deniedUserLocation()
        }
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
                        sendUiEffect(CourtEffect.ShowError("Failed to load courts: ${result.error}"))
                    }
                }
            },
            onError = { error ->
                uiStateMutable.update { it.copy(isLoading = false) }
                sendUiEffect(CourtEffect.ShowError("Network error: ${error.message}"))
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
        sendUiEffect(CourtEffect.NavigateToGameCreation(court))
    }

    private fun clickOnMap() {
        uiStateMutable.update {
            it.copy(selectedCourt = null, showDetails = false)
        }
    }
}
