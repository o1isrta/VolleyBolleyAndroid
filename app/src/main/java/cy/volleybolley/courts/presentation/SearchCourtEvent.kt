package cy.volleybolley.courts.presentation

import com.google.android.gms.maps.model.LatLng
import cy.volleybolley.core.presentation.base.UiEvent
import cy.volleybolley.courts.domain.model.Court

sealed interface SearchCourtEvent : UiEvent {
    object LoadSearchCourt : SearchCourtEvent
    object OnBackFromSearchCourtListScreen : SearchCourtEvent
    data class ClickOnSearchCourtMarker(val court: Court) : SearchCourtEvent
    data class ClickOnSearchCourtDetails(val court: Court) : SearchCourtEvent
    data class ClickOnChooseSearchCourt(val court: Court) : SearchCourtEvent
    object ClickOnMap : SearchCourtEvent
    data class UpdateUserLocation(val latLng: LatLng) : SearchCourtEvent
    object DeniedUserLocation : SearchCourtEvent
}
