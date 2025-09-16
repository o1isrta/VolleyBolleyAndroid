package cy.volleybolley.core.presentation.ui.screens.games.mygames.manageplayers

import cy.volleybolley.core.presentation.base.UiEvent

sealed interface ManagePlayersAction : UiEvent {
    data object ClickBack : ManagePlayersAction
    data class RemovePlayer(val index: Int) : ManagePlayersAction // заглушка
    data object Refresh : ManagePlayersAction                      // заглушка под домейн
}
