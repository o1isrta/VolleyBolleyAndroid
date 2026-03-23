package cy.volleybolley.core.presentation.ui.screens.createnewtourney

import cy.volleybolley.core.presentation.base.UiEvent

sealed interface TourneyEnteringConditionsScreenEvent : UiEvent {
    data object OnPublicSelected : TourneyEnteringConditionsScreenEvent
    data object OnPrivateSelected : TourneyEnteringConditionsScreenEvent
    data object OnBackClicked : TourneyEnteringConditionsScreenEvent
    data object OnAddPaymentClick : TourneyEnteringConditionsScreenEvent
    data object OnSaveTourneyClick : TourneyEnteringConditionsScreenEvent
    data class PerPersonChanged(val perPerson: String) : TourneyEnteringConditionsScreenEvent
    data class MaximumPlayersChanged(val maximumPlayers: Int) : TourneyEnteringConditionsScreenEvent
}
