package cy.volleybolley.courts.presentation

import cy.volleybolley.core.presentation.base.UiEffect
import cy.volleybolley.courts.presentation.model.CourtUi
import cy.volleybolley.games.domain.model.event.EventType

sealed interface SearchCourtEffect : UiEffect {
    data class ShowError(val message: String) : SearchCourtEffect

    data class NavigateToGameCreation(
        val selectedCourt: CourtUi,
        val eventType: EventType
    ) : SearchCourtEffect

    data object NavigateBack : SearchCourtEffect
}
