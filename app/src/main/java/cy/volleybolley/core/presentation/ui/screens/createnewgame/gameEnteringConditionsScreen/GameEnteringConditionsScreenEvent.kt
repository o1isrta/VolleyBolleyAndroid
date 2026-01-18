package cy.volleybolley.core.presentation.ui.screens.createnewgame.gameEnteringConditionsScreen

import cy.volleybolley.core.presentation.base.UiEvent

sealed interface GameEnteringConditionsScreenEvent : UiEvent {
    data object OnPublicSelected : GameEnteringConditionsScreenEvent
    data object OnPrivateSelected : GameEnteringConditionsScreenEvent
    data object OnBackClicked : GameEnteringConditionsScreenEvent
    data object OnAddPaymentClick : GameEnteringConditionsScreenEvent
    data object OnSaveGameClick : GameEnteringConditionsScreenEvent
    data object OnManagePlayersClick : GameEnteringConditionsScreenEvent
    data object CheckIfAccountExists : GameEnteringConditionsScreenEvent
    data class PerPersonChanged(val perPerson: String) : GameEnteringConditionsScreenEvent
    data class MaximumPlayersChanged(val maximumPersons: Int) : GameEnteringConditionsScreenEvent
    data class RemovePlayer(val index: Int) : GameEnteringConditionsScreenEvent
}
