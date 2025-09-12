package cy.volleybolley.core.presentation.ui.screens.games.archive.event

import cy.volleybolley.core.presentation.base.UiEvent
import cy.volleybolley.core.presentation.ui.screens.games.archive.datamodel.Game
import cy.volleybolley.courts.domain.model.Location

sealed interface ArchiveScreenEvent : UiEvent {
    data object ClickBack : ArchiveScreenEvent
    data object ClickCreateGame : ArchiveScreenEvent
    data class ClickDetails(val game: Game) : ArchiveScreenEvent
    data class ClickMap(val location: Location) : ArchiveScreenEvent
    data object Refresh : ArchiveScreenEvent
}
