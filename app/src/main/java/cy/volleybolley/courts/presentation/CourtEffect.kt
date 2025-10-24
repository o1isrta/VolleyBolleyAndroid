package cy.volleybolley.courts.presentation

import cy.volleybolley.core.presentation.base.UiEffect
import cy.volleybolley.courts.domain.model.Court

sealed interface CourtEffect : UiEffect {
    data class ShowError(val message: String) : CourtEffect

    data class NavigateToGameCreation(val selectedCourt: Court) : CourtEffect

    data object NavigateBack : CourtEffect
}
