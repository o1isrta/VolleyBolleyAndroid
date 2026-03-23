package cy.volleybolley.core.presentation.ui.screens.createnewtourney

import cy.volleybolley.core.presentation.base.UiEffect

sealed interface BasicTourneySetupEffect : UiEffect {
    data object NavigateBack : BasicTourneySetupEffect
    data object NavigateToCreatePlace : BasicTourneySetupEffect
    data class ShowErrorMessageById(val messageId: Int) : BasicTourneySetupEffect
    data class ShowErrorMessage(val message: String) : BasicTourneySetupEffect
    data object NavigateNextStep : BasicTourneySetupEffect
}
