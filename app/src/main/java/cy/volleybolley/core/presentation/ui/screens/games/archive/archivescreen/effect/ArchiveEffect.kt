package cy.volleybolley.core.presentation.ui.screens.games.archive.archivescreen.effect

import cy.volleybolley.core.presentation.base.UiEffect
import cy.volleybolley.courts.domain.model.Location

sealed interface ArchiveEffect : UiEffect {
    data object NavigateBack : ArchiveEffect
    data object NavigateToCreateGame : ArchiveEffect
    data object NavigateToPastGame : ArchiveEffect
    data object NavigateToPastTourney : ArchiveEffect
    data class OpenMap(val location: Location) : ArchiveEffect
}
