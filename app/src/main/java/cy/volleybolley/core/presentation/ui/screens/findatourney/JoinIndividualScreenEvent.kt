package cy.volleybolley.core.presentation.ui.screens.findatourney

import cy.volleybolley.core.presentation.base.UiEvent

sealed interface JoinIndividualScreenEvent : UiEvent {
    data object OnBackClicked : JoinIndividualScreenEvent
}
