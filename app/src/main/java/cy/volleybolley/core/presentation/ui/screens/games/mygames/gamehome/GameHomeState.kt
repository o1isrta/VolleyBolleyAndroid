package cy.volleybolley.core.presentation.ui.screens.games.mygames.gamehome

import cy.volleybolley.core.presentation.base.UiState

data class GameHomeState(
    val upcomingGame: String = "",
    val invites: Int = 0
) : UiState
