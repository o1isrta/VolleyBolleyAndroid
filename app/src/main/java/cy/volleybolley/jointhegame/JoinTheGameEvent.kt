package cy.volleybolley.jointhegame

import cy.volleybolley.core.presentation.base.UiEvent
import cy.volleybolley.courts.domain.model.Location

sealed interface JoinTheGameEvent : UiEvent {
    object OnBack : JoinTheGameEvent
    data class OnMap(val location: Location?) : JoinTheGameEvent
    object OnRefresh : JoinTheGameEvent
    object OnJoinGame : JoinTheGameEvent
}
