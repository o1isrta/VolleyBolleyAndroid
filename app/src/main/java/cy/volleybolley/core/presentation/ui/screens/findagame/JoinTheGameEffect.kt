package cy.volleybolley.core.presentation.ui.screens.findagame

import cy.volleybolley.core.presentation.base.UiEffect

sealed interface JoinTheGameEffect : UiEffect {
    object NavigateBack : JoinTheGameEffect
    data class OpenMap(val location: Location): JoinTheGameEffect
    object JoinGame: JoinTheGameEffect
}
