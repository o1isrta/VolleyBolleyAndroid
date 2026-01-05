package cy.volleybolley.core.presentation.ui.screens.createnewgame.basicGameSetupScreen

import cy.volleybolley.core.presentation.base.UiEffect

sealed class BasicGameSetupScreenEffect : UiEffect {
    object NavigateBack : BasicGameSetupScreenEffect()
    object NavigateToCreatePlace : BasicGameSetupScreenEffect()
    data class ShowErrorMessageById(val messageId: Int) : BasicGameSetupScreenEffect()
    data class ShowErrorMessage(val message: String) : BasicGameSetupScreenEffect()
    object NavigateNextStep : BasicGameSetupScreenEffect()
}
