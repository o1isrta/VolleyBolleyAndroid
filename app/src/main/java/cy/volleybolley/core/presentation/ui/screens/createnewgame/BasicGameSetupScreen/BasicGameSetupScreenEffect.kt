package cy.volleybolley.core.presentation.ui.screens.createnewgame.BasicGameSetupScreen

import android.widget.Toast
import cy.volleybolley.core.presentation.base.UiEffect
import cy.volleybolley.core.presentation.ui.screens.createnewgame.GameEnteringConditionsScreen.GameEnteringConditionsScreenEffect

sealed class BasicGameSetupScreenEffect : UiEffect {
    object NavigateBack: BasicGameSetupScreenEffect()
    object NavigateToCreatePlace : BasicGameSetupScreenEffect()
    data class ShowError(val message: String) : BasicGameSetupScreenEffect()
    object NavigateNextStep : BasicGameSetupScreenEffect()
}
