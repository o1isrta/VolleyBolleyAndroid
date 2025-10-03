package cy.volleybolley.core.presentation.ui.screens.games.archive.archivescreen.effect

import cy.volleybolley.core.presentation.base.UiEffect
import cy.volleybolley.core.presentation.ui.navigation.NavMap
import cy.volleybolley.courts.domain.model.Location

sealed interface ArchiveEffect : UiEffect {
    data object NavigateBack : ArchiveEffect
    data class Navigate(val route: NavMap) : ArchiveEffect
    data class OpenMap(val location: Location) : ArchiveEffect
}
