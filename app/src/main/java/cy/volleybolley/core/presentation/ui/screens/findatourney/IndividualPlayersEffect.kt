package cy.volleybolley.core.presentation.ui.screens.findatourney

import cy.volleybolley.core.presentation.base.UiEffect

sealed interface IndividualPlayersEffect : UiEffect {
    data object NavigateBack : IndividualPlayersEffect
}
