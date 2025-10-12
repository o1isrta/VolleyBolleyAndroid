package cy.volleybolley.core.presentation.ui.screens.games.archive.pasttourneyscreen.event

import cy.volleybolley.core.presentation.base.UiEvent
import cy.volleybolley.core.presentation.ui.screens.games.archive.datamodel.ShortTeam
import cy.volleybolley.courts.domain.model.Location

sealed interface PastTourneyEvent : UiEvent {
    data object OnBackClick : PastTourneyEvent
    data class OnMapClick(val location: Location) : PastTourneyEvent
    data class OnJoinedPlayersClick(val teams: List<ShortTeam>) : PastTourneyEvent
    data object Refresh : PastTourneyEvent
}
