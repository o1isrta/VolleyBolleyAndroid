package cy.volleybolley.core.presentation.ui.screens.findatourney

import cy.volleybolley.core.presentation.base.UiEvent

sealed interface InvitePlayersEvent : UiEvent {
    data object OnBackClicked : InvitePlayersEvent
}
