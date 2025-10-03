package cy.volleybolley.core.presentation.ui.screens.games.archive.archivescreen.event

import cy.volleybolley.core.presentation.base.UiEvent
import cy.volleybolley.core.presentation.ui.screens.games.archive.datamodel.Game
import cy.volleybolley.courts.domain.model.Location

sealed interface ArchiveEvent : UiEvent {
    data object ClickBack : ArchiveEvent
    data object ClickCreateGame : ArchiveEvent
    data class ClickDetails(val game: Game) : ArchiveEvent
    data class ClickMap(val location: Location) : ArchiveEvent
    data object Refresh : ArchiveEvent
}
