package cy.volleybolley.core.presentation.ui.screens.findatourney

import cy.volleybolley.core.presentation.base.UiEvent

sealed interface JoinIndividualEvent : UiEvent {
    data object OnBackClicked : JoinIndividualEvent
}
