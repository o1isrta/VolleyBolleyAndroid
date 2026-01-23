package cy.volleybolley.courts.presentation

import com.google.android.gms.maps.model.LatLng
import cy.volleybolley.core.presentation.base.UiState
import cy.volleybolley.courts.presentation.model.CourtUi

data class SearchCourtState(
    val courts: List<CourtUi> = emptyList(),
    val selectedCourt: CourtUi? = null,
    val userLocation: LatLng? = null,
    val showDetails: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
) : UiState
