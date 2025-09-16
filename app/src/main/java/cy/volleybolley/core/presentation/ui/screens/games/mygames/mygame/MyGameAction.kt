package cy.volleybolley.core.presentation.ui.screens.games.mygames.mygame

import cy.volleybolley.core.presentation.base.UiEvent

sealed interface MyGameAction : UiEvent {
    data object ClickBack : MyGameAction
    data class ClickMap(val location: Location) : MyGameAction
    data object Refresh : MyGameAction

    // Заглушки
    data class DeletePlayer(val index: Int) : MyGameAction
    data object ClickInvite : MyGameAction
    data object ClickShare : MyGameAction
    data object ClickCancel : MyGameAction
}
