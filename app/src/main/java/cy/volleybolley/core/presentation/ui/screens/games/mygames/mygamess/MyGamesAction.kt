package cy.volleybolley.core.presentation.ui.screens.games.mygames.mygamess

import cy.volleybolley.core.presentation.base.UiEvent

sealed interface MyGamesAction : UiEvent {
    data object ClickBack : MyGamesAction
    data object ClickCreateGame : MyGamesAction
    data class ClickDetails(val details: GameDetails) : MyGamesAction
    data class ClickMap(val location: Location) : MyGamesAction
    data object Refresh : MyGamesAction
}
