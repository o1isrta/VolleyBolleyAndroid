package cy.volleybolley.core.presentation.ui.screens.games.mygames.manageplayers

import cy.volleybolley.core.presentation.base.UiEffect

sealed interface ManagePlayersEffect : UiEffect {
    data object NavigateBack : ManagePlayersEffect
}
