package cy.volleybolley.core.presentation.ui.screens.games.mygames.mygame


sealed interface MyGameEffect {
    data object NavigateBack : MyGameEffect
    data class OpenMap(val location: Location) : MyGameEffect

    // Заглушки
    data object ShareLink : MyGameEffect
    data object InvitePlayers : MyGameEffect
    data object CancelGame : MyGameEffect
}
