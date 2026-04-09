package cy.volleybolley.profile.presentation.ui.screens.players

import cy.volleybolley.core.presentation.base.UiState
import cy.volleybolley.players.domain.model.Player

data class PlayersScreenState(
    val searchText: String = "",
    val showAllPlayers: Boolean = true,
    val players: List<Player> = listOf(),
) : UiState
