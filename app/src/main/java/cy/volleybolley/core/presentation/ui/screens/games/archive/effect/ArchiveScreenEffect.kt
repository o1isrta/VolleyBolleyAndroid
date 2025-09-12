package cy.volleybolley.core.presentation.ui.screens.games.archive.effect

import cy.volleybolley.core.presentation.base.UiEffect
import cy.volleybolley.courts.domain.model.Location

sealed interface ArchiveScreenEffect : UiEffect {
    data object NavigateBack : ArchiveScreenEffect
    data class Navigate(val route: Any) : ArchiveScreenEffect
    data class OpenMap(val location: Location) : ArchiveScreenEffect
}
