package cy.volleybolley.core.presentation.ui.screens.createnewgame.PrivacyOptionsScreen

import cy.volleybolley.core.presentation.base.UiState
import cy.volleybolley.core.presentation.ui.screens.createnewgame.GameEnteringConditionsScreen.PlayerUI

private const val LEVEL_HIGH = "H"
private const val LEVEL_LIGHT = "L"
private const val LEVEL_MEDIUM = "M"
private const val LEVEL_PRO = "P"

data class PrivacyOptionsScreenState (
    val flagFavorites: Boolean = false, // флаг = true - искать игроков среди favorites, false - среди всех
    val errorMessage: String? = null,
    val isLoading : Boolean = false,     // Для загрузки (если необходимо)
    val players: List<PlayerUI> = listOf(
        PlayerUI("Kristina Popova", LEVEL_MEDIUM),
        PlayerUI("Polina Vasylyeva", LEVEL_MEDIUM),
        PlayerUI("Anton Ivanov", LEVEL_LIGHT),
        PlayerUI("Aleksandr Abramov", LEVEL_HIGH),
        PlayerUI("Maria Kuznetsova", LEVEL_PRO)
    )
) : UiState
