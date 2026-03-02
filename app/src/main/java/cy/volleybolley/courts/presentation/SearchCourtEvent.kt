package cy.volleybolley.courts.presentation

import com.google.android.gms.maps.model.LatLng
import cy.volleybolley.core.presentation.base.UiEvent
import cy.volleybolley.courts.presentation.model.CourtUi

sealed interface SearchCourtEvent : UiEvent {
    object LoadSearchCourt : SearchCourtEvent
    object OnBackFromSearchCourtListScreen : SearchCourtEvent
    data class ClickOnCourtMarker(val court: CourtUi) : SearchCourtEvent
    data class ToggleCourtDetailsFromMap(val court: CourtUi) : SearchCourtEvent
    object ClickOnMap : SearchCourtEvent
    data class ClickOnCourtFromList(val court: CourtUi) : SearchCourtEvent
    data class ClickOnChooseSearchCourt(val court: CourtUi) : SearchCourtEvent
    data class UpdateUserLocation(val latLng: LatLng) : SearchCourtEvent
    object DeniedUserLocation : SearchCourtEvent
}
