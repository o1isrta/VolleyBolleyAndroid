package cy.volleybolley.core.presentation.ui.screens.games.mygames.gamehome

import cy.volleybolley.core.presentation.base.UiEffect

sealed interface GameHomeEffect : UiEffect {
    data class Navigate(val route: Any) : GameHomeEffect
}
