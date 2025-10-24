package cy.volleybolley.courts.presentation

import com.google.android.gms.maps.model.LatLng
import cy.volleybolley.core.presentation.base.UiState
import cy.volleybolley.courts.domain.model.Court

data class CourtState(
    val courts: List<Court> = emptyList(),
    val selectedCourt: Court? = null,
    val userLocation: LatLng? = null,
    val showDetails: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
) : UiState
