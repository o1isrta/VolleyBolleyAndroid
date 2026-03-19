package cy.volleybolley.core.presentation.ui.screens.games.mygames.mygamess

import cy.volleybolley.core.presentation.base.UiEffect

sealed interface MyGamesEffect : UiEffect {
    data object NavigateBack : MyGamesEffect
    data object NavigateToCreateGame : MyGamesEffect
    data object NavigateToMyGame : MyGamesEffect
    data object NavigateToMyTourney : MyGamesEffect
    data class OpenMap(val location: Location) : MyGamesEffect
}
