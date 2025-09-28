package cy.volleybolley.core.presentation.ui.screens.createnewgame.GameEnteringConditionsScreen

import cy.volleybolley.core.presentation.base.UiEffect

sealed class GameEnteringConditionsScreenEffect : UiEffect{
    object NavigateToPayments : GameEnteringConditionsScreenEffect()  // Навигация к экрану создания аккаунта (PaymentsScreen )
    data class ShowError(val message: String) : GameEnteringConditionsScreenEffect()
    object NavigateBack :  GameEnteringConditionsScreenEffect()
}
