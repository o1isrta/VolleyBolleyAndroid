package cy.volleybolley.core.presentation.ui.screens.createnewgame.GameEnteringConditionsScreen

import cy.volleybolley.core.presentation.base.UiState
import cy.volleybolley.core.presentation.ui.GENDER_FEMALE
import cy.volleybolley.core.presentation.ui.GENDER_MALE
import cy.volleybolley.players.domain.model.Player

private const val LEVEL_HIGH = "H"
private const val LEVEL_LIGHT = "L"
private const val LEVEL_MEDIUM = "M"

data class GameEnteringConditionsScreenState (
    val maximumPlayers: Int = 8,
    val perPerson: String = "5.0",
    val accountNumber: String? = null,  // номер аккаунта, если есть
    val errorMessage: String? = null,
    val isLoading : Boolean = false,     // Для загрузки (если необходимо)
    val players: List<Player> = listOf(
        Player(1,"Kristina", "Popova", null, true, GENDER_FEMALE, LEVEL_MEDIUM),
        Player(2, "Polina", "Vasylyeva", null,false, GENDER_FEMALE, LEVEL_MEDIUM),
        Player(3, "Anton", "Ivanov", null, true, GENDER_MALE, LEVEL_LIGHT),
        Player(4, "Aleksandr", "Abramov", null, false, GENDER_MALE, LEVEL_HIGH)
    )
) : UiState


