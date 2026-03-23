package cy.volleybolley.core.presentation.ui.screens.games.mygames.mygame

import cy.volleybolley.core.presentation.base.UiEffect

sealed interface MyGameEffect : UiEffect {
    data object NavigateBack : MyGameEffect
    data class OpenMap(val location: Location) : MyGameEffect

    // Заглушки
    data object ShareLink : MyGameEffect
    data object InvitePlayers : MyGameEffect
    data object CancelGame : MyGameEffect
}
