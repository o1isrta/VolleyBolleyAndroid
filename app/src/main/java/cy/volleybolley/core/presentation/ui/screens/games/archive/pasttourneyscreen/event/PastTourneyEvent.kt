package cy.volleybolley.core.presentation.ui.screens.games.archive.pasttourneyscreen.event

import cy.volleybolley.core.presentation.base.UiEvent
import cy.volleybolley.courts.domain.model.Location
import cy.volleybolley.games.domain.model.entity.Team

sealed interface PastTourneyEvent : UiEvent {
    data object OnBackClick : PastTourneyEvent
    data class OnMapClick(val location: Location) : PastTourneyEvent
    data class OnJoinedPlayersClick(val teams: List<Team>) : PastTourneyEvent
    data object Refresh : PastTourneyEvent
}
