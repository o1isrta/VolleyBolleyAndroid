package cy.volleybolley.courts.presentation

import com.google.android.gms.maps.model.LatLng
import cy.volleybolley.core.presentation.base.UiEvent
import cy.volleybolley.courts.domain.model.Court

sealed interface CourtEvent : UiEvent {
    object LoadCourts : CourtEvent
    object OnBackFromCourtListScreen : CourtEvent
    data class ClickOnCourtMarker(val court: Court) : CourtEvent
    data class ClickOnCourtDetails(val court: Court) : CourtEvent
    data class ClickOnChooseCourt(val court: Court) : CourtEvent
    object ClickOnMap : CourtEvent
    data class UpdateUserLocation(val latLng: LatLng) : CourtEvent
    object DeniedUserLocation : CourtEvent
}
