package cy.volleybolley.core.presentation.ui.screens.createNewGame.gameConditions

import cy.volleybolley.core.presentation.base.UiEffect

sealed interface GameConditionsEffect : UiEffect {
    data object NavigateToPayments : GameConditionsEffect
    data object NavigateToSuccess : GameConditionsEffect
    data class ShowErrorMessage(val message: String) : GameConditionsEffect
    data object NavigateBack : GameConditionsEffect
    data object NavigateToPrivacy : GameConditionsEffect
}
