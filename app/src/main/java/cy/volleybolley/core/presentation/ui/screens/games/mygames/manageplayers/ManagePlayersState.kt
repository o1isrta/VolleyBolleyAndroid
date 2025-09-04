package cy.volleybolley.core.presentation.ui.screens.games.mygames.manageplayers

// Заглушка
data class ManagePlayersState(
    val placeholder: Unit = Unit
)

data class PlayerUi(
    val name: String?,  // null => Free spot
    val level: String?  // null => hide badge
)
