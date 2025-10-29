package cy.volleybolley.courts.presentation

import cy.volleybolley.core.presentation.base.UiEffect
import cy.volleybolley.courts.domain.model.Court

sealed interface SearchCourtEffect : UiEffect {
    data class ShowError(val message: String) : SearchCourtEffect

    data class NavigateToGameCreation(val selectedCourt: Court) : SearchCourtEffect

    data object NavigateBack : SearchCourtEffect
}
