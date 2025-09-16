package cy.volleybolley.core.presentation.ui.screens.games.mygames.mygamess

import cy.volleybolley.core.presentation.base.UiEffect

sealed interface MyGamesEffect : UiEffect {
    data object NavigateBack : MyGamesEffect
    data class Navigate(val route: Any) : MyGamesEffect
    data class OpenMap(val location: Location) : MyGamesEffect
}
