package cy.volleybolley.core.presentation.ui.screens.games.mygames.manageplayers

sealed interface ManagePlayersAction {
    data object ClickBack : ManagePlayersAction
    data class RemovePlayer(val index: Int) : ManagePlayersAction // заглушка
    data object Refresh : ManagePlayersAction                      // заглушка под домейн
}
