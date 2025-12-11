package cy.volleybolley.core.presentation.ui.screens.createnewgame.BasicGameSetupScreen

import cy.volleybolley.core.presentation.base.UiEffect

sealed class BasicGameSetupScreenEffect : UiEffect {
    object NavigateBack : BasicGameSetupScreenEffect()
    object NavigateToCreatePlace : BasicGameSetupScreenEffect()
    data class ShowError(val message: String) : BasicGameSetupScreenEffect()
    object NavigateNextStep : BasicGameSetupScreenEffect()
}
