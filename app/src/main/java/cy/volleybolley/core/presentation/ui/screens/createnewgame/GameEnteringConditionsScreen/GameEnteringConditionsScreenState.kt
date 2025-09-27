package cy.volleybolley.core.presentation.ui.screens.createnewgame.GameEnteringConditionsScreen

import cy.volleybolley.core.presentation.base.UiState

data class GameEnteringConditionsScreenState (
    val maximumPlayers: Int = 4,
    val selectedPrivacy: Privacy = Privacy.Public,
    val perPerson: Double = 5.0,
    val hasAccount: Boolean = false,    // есть ли аккаунт
    val accountNumber: String? = null,  // номер аккаунта, если есть
    val errorMessage: String? = null,
    val isLoading : Boolean = false     // Для загрузки (если необходимо)
) : UiState

enum class Privacy {
    Public,
    Private
}



