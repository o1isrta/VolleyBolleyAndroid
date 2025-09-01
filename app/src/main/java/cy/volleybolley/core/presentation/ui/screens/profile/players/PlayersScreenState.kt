package cy.volleybolley.core.presentation.ui.screens.profile.players

import cy.volleybolley.core.presentation.base.UiState
import cy.volleybolley.core.presentation.ui.screens.profile.players.model.PlayerTemp

data class PlayersScreenState(
    val searchText: String = "",
    val showAllPlayers: Boolean = true,
    val players: List<PlayerTemp> = listOf(),
) : UiState
