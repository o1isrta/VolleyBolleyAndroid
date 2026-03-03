package cy.volleybolley.core.presentation.ui.screens.createnewtourney

import cy.volleybolley.core.presentation.base.UiEffect

sealed interface BasicTourneySetupScreenEffect : UiEffect {
    data object NavigateBack : BasicTourneySetupScreenEffect
    data object NavigateToCreatePlace : BasicTourneySetupScreenEffect
    data class ShowErrorMessageById(val messageId: Int) : BasicTourneySetupScreenEffect
    data class ShowErrorMessage(val message: String) : BasicTourneySetupScreenEffect
    data object NavigateNextStep : BasicTourneySetupScreenEffect
}
