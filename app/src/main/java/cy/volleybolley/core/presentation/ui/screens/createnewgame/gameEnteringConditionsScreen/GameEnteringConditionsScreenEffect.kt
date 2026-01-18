package cy.volleybolley.core.presentation.ui.screens.createnewgame.gameEnteringConditionsScreen

import cy.volleybolley.core.presentation.base.UiEffect

sealed interface GameEnteringConditionsScreenEffect : UiEffect {
    data object NavigateToPayments : GameEnteringConditionsScreenEffect
    data object NavigateToSuccess : GameEnteringConditionsScreenEffect
    data class ShowErrorMessage(val message: String) : GameEnteringConditionsScreenEffect
    data object NavigateBack : GameEnteringConditionsScreenEffect
    data object NavigateToPrivacy : GameEnteringConditionsScreenEffect
}
