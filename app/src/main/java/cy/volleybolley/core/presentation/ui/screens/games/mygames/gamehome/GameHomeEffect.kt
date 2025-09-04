package cy.volleybolley.core.presentation.ui.screens.games.mygames.gamehome

sealed interface GameHomeEffect {
    data class Navigate(val route: Any) : GameHomeEffect
}
