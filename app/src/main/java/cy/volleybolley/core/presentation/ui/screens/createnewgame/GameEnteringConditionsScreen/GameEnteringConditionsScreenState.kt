package cy.volleybolley.core.presentation.ui.screens.createnewgame.GameEnteringConditionsScreen

import cy.volleybolley.core.presentation.base.UiState

data class GameEnteringConditionsScreenState (
    val maximumPlayers: Int = 8,
    val selectedPrivacy: Privacy = Privacy.Private,
    val perPerson: String = "5.0",
    val accountNumber: String? = null,  // номер аккаунта, если есть
    val errorMessage: String? = null,
    val isLoading : Boolean = false     // Для загрузки (если необходимо)
) : UiState

enum class Privacy {
    Public,
    Private
}



