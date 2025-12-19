package cy.volleybolley.core.presentation.ui.screens.createnewgame.gameEnteringConditionsScreen

import cy.volleybolley.core.presentation.base.UiState
import cy.volleybolley.core.presentation.ui.GENDER_FEMALE
import cy.volleybolley.core.presentation.ui.GENDER_MALE
import cy.volleybolley.players.domain.model.Player

private const val LEVEL_HIGH = "H"
private const val LEVEL_LIGHT = "L"
private const val LEVEL_MEDIUM = "M"
private const val KRISTINA_ID = 1
private const val POLINA_ID = 2
private const val ANTON_ID = 3
private const val ALEKSANDR_ID = 4

data class GameEnteringConditionsScreenState(
    val maximumPlayers: Int = 8,
    val perPerson: String = "5.0",
    val accountNumber: String? = null, // номер аккаунта, если есть
    val errorMessage: String? = null,
    val isLoading: Boolean = false, // Для загрузки (если необходимо)
    val players: List<Player> = listOf(
        Player(KRISTINA_ID, "Kristina", "Popova", null, true, GENDER_FEMALE, LEVEL_MEDIUM),
        Player(POLINA_ID, "Polina", "Vasylyeva", null, false, GENDER_FEMALE, LEVEL_MEDIUM),
        Player(ANTON_ID, "Anton", "Ivanov", null, true, GENDER_MALE, LEVEL_LIGHT),
        Player(ALEKSANDR_ID, "Aleksandr", "Abramov", null, false, GENDER_MALE, LEVEL_HIGH)
    )
) : UiState
