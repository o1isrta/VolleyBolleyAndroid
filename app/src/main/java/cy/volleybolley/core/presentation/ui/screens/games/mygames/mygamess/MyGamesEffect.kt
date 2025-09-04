package cy.volleybolley.core.presentation.ui.screens.games.mygames.mygamess

sealed interface MyGamesEffect {
    data object NavigateBack : MyGamesEffect
    data class Navigate(val route: Any) : MyGamesEffect
    data class OpenMap(val location: Location) : MyGamesEffect
}
