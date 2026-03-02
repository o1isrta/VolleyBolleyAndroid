package cy.volleybolley.core.presentation.ui.screens.games.archive.pastgamescreen.event

import cy.volleybolley.core.presentation.base.UiEvent
import cy.volleybolley.courts.domain.model.Location

sealed interface PastGameEvent : UiEvent {
    data object OnBackClick : PastGameEvent
    data class OnMapClick(val location: Location) : PastGameEvent
    data object Refresh : PastGameEvent
}
