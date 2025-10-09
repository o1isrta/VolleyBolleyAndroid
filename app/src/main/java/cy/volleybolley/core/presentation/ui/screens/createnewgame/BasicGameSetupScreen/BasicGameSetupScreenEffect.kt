package cy.volleybolley.core.presentation.ui.screens.createnewgame.BasicGameSetupScreen

import cy.volleybolley.core.presentation.base.UiEffect
import cy.volleybolley.core.presentation.ui.screens.createnewgame.GameEnteringConditionsScreen.GameEnteringConditionsScreenEffect

sealed class BasicGameSetupScreenEffect : UiEffect {
     // object NavigateToPayments : GameEnteringConditionsScreenEffect()  // Навигация к экрану создания аккаунта (PaymentsScreen )
     //  data class ShowError(val message: String) : GameEnteringConditionsScreenEffect()
    object NavigateBack: BasicGameSetupScreenEffect()
    object NavigateToCreatePlace : BasicGameSetupScreenEffect()
}
