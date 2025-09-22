package cy.volleybolley.core.presentation.ui.screens.findagame

import cy.volleybolley.core.presentation.base.UiEvent

sealed interface JoinTheGameEvent : UiEvent {
    object OnBack: JoinTheGameEvent
    object OnMap: JoinTheGameEvent
    object Refresh: JoinTheGameEvent
    object OnJoinGame: JoinTheGameEvent
}
