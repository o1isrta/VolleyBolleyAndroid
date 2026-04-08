package cy.volleybolley.core.presentation.ui.screens.games.createNewGame.gameConditionsScreen

import cy.volleybolley.core.presentation.base.UiEvent

sealed interface GameConditionsEvent : UiEvent {
    data object OnPublicSelected : GameConditionsEvent
    data object OnPrivateSelected : GameConditionsEvent
    data object OnBackClicked : GameConditionsEvent
    data object OnAddPaymentClick : GameConditionsEvent
    data object OnSaveGameClick : GameConditionsEvent
    data object OnManagePlayersClick : GameConditionsEvent
    data object CheckIfAccountExists : GameConditionsEvent
    data class PerPersonChanged(val perPerson: String) : GameConditionsEvent
    data class MaximumPlayersChanged(val maximumPlayers: Int) : GameConditionsEvent
    data class RemovePlayer(val index: Int) : GameConditionsEvent
}
