package cy.volleybolley.core.presentation.ui.screens.games.mygames.manageplayers

sealed interface ManagePlayersEffect {
    data object NavigateBack : ManagePlayersEffect
}
