package cy.volleybolley.core.presentation.ui.screens.createnewtourney

import cy.volleybolley.core.presentation.base.UiEffect

sealed interface TourneyEnteringConditionsScreenEffect : UiEffect {
    data object NavigateToPayments : TourneyEnteringConditionsScreenEffect
    data object NavigateToSuccess : TourneyEnteringConditionsScreenEffect
    data class ShowErrorMessage(val message: String) : TourneyEnteringConditionsScreenEffect
    data object NavigateBack : TourneyEnteringConditionsScreenEffect
    data object NavigateToChangeTeam : TourneyEnteringConditionsScreenEffect
}
