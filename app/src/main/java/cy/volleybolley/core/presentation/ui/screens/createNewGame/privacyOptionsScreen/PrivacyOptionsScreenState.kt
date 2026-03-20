package cy.volleybolley.core.presentation.ui.screens.createNewGame.privacyOptionsScreen

import cy.volleybolley.core.presentation.base.UiState
import cy.volleybolley.players.domain.model.Player

data class PrivacyOptionsScreenState(
    val flagFavorites: Boolean = false,
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val playersSearchResult: List<Player> = emptyList(),
    val query: String = "",
    val selectedPlayers: Set<Player> = emptySet(),
    val filteredPlayers: List<Player> = emptyList()
) : UiState
