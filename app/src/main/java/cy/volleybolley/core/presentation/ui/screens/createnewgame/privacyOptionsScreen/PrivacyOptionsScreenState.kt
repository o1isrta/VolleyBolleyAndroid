package cy.volleybolley.core.presentation.ui.screens.createnewgame.privacyOptionsScreen

import cy.volleybolley.core.presentation.base.UiState
import cy.volleybolley.players.domain.model.Player

data class PrivacyOptionsScreenState(
    val flagFavorites: Boolean = false, // флаг = true - искать игроков среди favorites, false - среди всех
    val errorMessage: String? = null,
    val isLoading: Boolean = false, // Для загрузки
    val playersSearchResult: List<Player> = emptyList(), // Список игроков для отображения в поиске
    val query: String = "", // для отслеживания текущего поискового запроса
    val selectedPlayers: MutableSet<Player> = mutableSetOf() // Игроки, которых пользователь выбрал
) : UiState
