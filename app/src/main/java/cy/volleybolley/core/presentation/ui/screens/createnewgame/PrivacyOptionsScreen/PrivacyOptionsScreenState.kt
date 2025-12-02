package cy.volleybolley.core.presentation.ui.screens.createnewgame.PrivacyOptionsScreen

import cy.volleybolley.core.presentation.base.UiState
import cy.volleybolley.players.domain.model.Player

//private const val LEVEL_HIGH = "H"
//private const val LEVEL_LIGHT = "L"
//private const val LEVEL_MEDIUM = "M"
//private const val LEVEL_PRO = "P"

//data class PrivacyOptionsScreenState (
//    val flagFavorites: Boolean = false, // флаг = true - искать игроков среди favorites, false - среди всех
//    val errorMessage: String? = null,
//    val isLoading : Boolean = false,     // Для загрузки
//    val playersSearchResult: List<Player> = listOf(
//        Player(1,"Kristina", "Popova", null, true, cy.volleybolley.core.presentation.ui.LEVEL_MEDIUM),
//        Player(2, "Polina", "Vasylyeva", null,false, cy.volleybolley.core.presentation.ui.LEVEL_PRO),
//        Player(3, "Anton", "Ivanov", null, true, cy.volleybolley.core.presentation.ui.LEVEL_LIGHT),
//        Player(4, "Aleksandr", "Abramov", null, false, cy.volleybolley.core.presentation.ui.LEVEL_HIGH),
//        Player(4, "Maria", "Novak", null, false, cy.volleybolley.core.presentation.ui.LEVEL_PRO)
//    ), // emptyList() // Список игроков для отображения в поиске
//    val query: String = "", // для отслеживания текущего поискового запроса
//    val selectedPlayers: MutableSet<Player> = mutableSetOf() // Игроки, которых пользователь выбрал
//) : UiState
data class PrivacyOptionsScreenState (
    val flagFavorites: Boolean = false, // флаг = true - искать игроков среди favorites, false - среди всех
    val errorMessage: String? = null,
    val isLoading : Boolean = false,     // Для загрузки
    val playersSearchResult: List<PrivacyOptionsPlayer> =  emptyList(), // Список игроков для отображения в поиске
    val query: String = "" // для отслеживания текущего поискового запроса
) : UiState

data class PrivacyOptionsPlayer(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val profilePictureUrl: String?,
    val isFavorite: Boolean // Специально для PrivacyOptionsScreen
    // Можно добавить другие UI-специфичные свойства, например, состояние чек-бокса
)
