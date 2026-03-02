package cy.volleybolley.core.presentation.ui.screens.games.archive.pastgamescreen.effect

import cy.volleybolley.core.presentation.base.UiEffect
import cy.volleybolley.courts.domain.model.Location

sealed interface PastGameEffect : UiEffect {
    data object NavigateBack : PastGameEffect
    data class OpenMap(val location: Location) : PastGameEffect
}
