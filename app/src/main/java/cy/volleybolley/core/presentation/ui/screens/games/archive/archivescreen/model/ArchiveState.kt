package cy.volleybolley.core.presentation.ui.screens.games.archive.archivescreen.model

import cy.volleybolley.core.presentation.base.UiState
import cy.volleybolley.core.presentation.ui.screens.games.archive.util.provideMockItem
import cy.volleybolley.games.domain.model.event.Event
import cy.volleybolley.games.domain.model.event.EventType

sealed interface ArchiveState : UiState {
    data object Loading : ArchiveState
    data object Error : ArchiveState
    data class Content(
        val competitionEvents: List<Event> = listOf(
            provideMockItem(1, EventType.GAME),
            provideMockItem(2, EventType.TOURNAMENT),
            provideMockItem(1, EventType.GAME),
            provideMockItem(2, EventType.TOURNAMENT),
        )
    ) : ArchiveState

    data object Empty : ArchiveState
}
