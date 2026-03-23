package cy.volleybolley.core.presentation.ui.screens.games.archive.archivescreen.event

import cy.volleybolley.core.presentation.base.UiEvent
import cy.volleybolley.courts.domain.model.Location
import cy.volleybolley.games.domain.model.event.Event

sealed interface ArchiveEvent : UiEvent {
    data object ClickBack : ArchiveEvent
    data object ClickCreateGame : ArchiveEvent
    data class ClickDetails(val competitionEvent: Event) : ArchiveEvent
    data class ClickMap(val location: Location) : ArchiveEvent
    data object Refresh : ArchiveEvent
}
