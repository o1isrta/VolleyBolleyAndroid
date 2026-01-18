package cy.volleybolley.core.presentation.ui.screens.createnewgame.basicGameSetupScreen

import cy.volleybolley.core.presentation.base.UiEffect

sealed interface BasicGameSetupScreenEffect : UiEffect {
    data object NavigateBack : BasicGameSetupScreenEffect
    data object NavigateToCreatePlace : BasicGameSetupScreenEffect
    data class ShowErrorMessageById(val messageId: Int) : BasicGameSetupScreenEffect
    data class ShowErrorMessage(val message: String) : BasicGameSetupScreenEffect
    data object NavigateNextStep : BasicGameSetupScreenEffect
}
