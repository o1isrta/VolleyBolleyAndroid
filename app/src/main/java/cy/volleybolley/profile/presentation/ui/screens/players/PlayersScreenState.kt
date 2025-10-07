package cy.volleybolley.profile.presentation.ui.screens.players

import cy.volleybolley.core.presentation.base.UiState
import cy.volleybolley.profile.presentation.ui.screens.players.model.PlayerTemp

data class PlayersScreenState(
    val searchText: String = "",
    val showAllPlayers: Boolean = true,
    val players: List<PlayerTemp> = listOf(),
) : UiState
