package cy.volleybolley.core.presentation.ui.screens.joinagame

import cy.volleybolley.core.presentation.base.UiEvent

sealed interface JoinTheGameEvent : UiEvent {
    object OnBack : JoinTheGameEvent
    data class OnMap(val location: Location) : JoinTheGameEvent
    object OnRefresh : JoinTheGameEvent
    object OnJoinGame : JoinTheGameEvent
}
