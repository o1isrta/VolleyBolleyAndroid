package cy.volleybolley.core.presentation.ui.screens.games.mygames.manageplayers

import cy.volleybolley.core.presentation.base.UiState

// Заглушка
data class ManagePlayersState(
    val placeholder: Unit = Unit
) : UiState

data class PlayerUi(
    val name: String?, // null => Free spot
    val level: String? // null => hide badge
)
