package cy.volleybolley.core.presentation.ui.screens.createNewGame.gameConditions

import cy.volleybolley.core.presentation.base.UiState
import cy.volleybolley.players.domain.model.Player

data class GameConditionsState(
    val maximumPlayers: Int = 8,
    val perPerson: String = "5.0",
    val accountNumber: String? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val players: List<Player> = emptyList(),
    val isPrivate: Boolean = false
) : UiState
