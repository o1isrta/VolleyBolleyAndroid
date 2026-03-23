package cy.volleybolley.core.presentation.ui.screens.games.gameinvites

import cy.volleybolley.core.presentation.base.UiEvent

sealed interface GameInvitesEvent : UiEvent {
    data object OnBackClicked : GameInvitesEvent
}
