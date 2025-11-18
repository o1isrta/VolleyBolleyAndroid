package cy.volleybolley.core.presentation.ui.screens.createnewgame.GameEnteringConditionsScreen

import cy.volleybolley.core.presentation.base.UiState
import cy.volleybolley.players.domain.model.Player

private const val LEVEL_HIGH = "H"
private const val LEVEL_LIGHT = "L"
private const val LEVEL_MEDIUM = "M"

data class GameEnteringConditionsScreenState (
    val maximumPlayers: Int = 8,
    val selectedPrivacy: Privacy = Privacy.Public,
    val perPerson: String = "5.0",
    val accountNumber: String? = null,  // номер аккаунта, если есть
    val errorMessage: String? = null,
    val isLoading : Boolean = false,     // Для загрузки (если необходимо)
    val players: List<Player> = listOf(
        Player(1,"Kristina", "Popova", null, true, LEVEL_MEDIUM),
        Player(2, "Polina", "Vasylyeva", null,false, LEVEL_MEDIUM),
        Player(3, "Anton", "Ivanov", null, true, LEVEL_LIGHT),
        Player(4, "Aleksandr", "Abramov", null, false, LEVEL_HIGH)
    )
//    val players: List<PlayerUI> = listOf(
//        PlayerUI("Kristina Popova", LEVEL_MEDIUM),
//        PlayerUI("Polina Vasylyeva", LEVEL_MEDIUM),
//        PlayerUI("Anton Ivanov", LEVEL_LIGHT),
//        PlayerUI("Aleksandr Abramov", LEVEL_HIGH)
//    )
) : UiState

enum class Privacy {
    Public,
    Private
}

// по образу MemberUi из 62 ветки, из файла ChangeTeamScreen.kt
//data class PlayerUI(
//    val name: String,//?, // null => Free spot
//    val level: String//? // null => нет бейджа
//)


