package cy.volleybolley.core.presentation.ui.screens.findatourney

import cy.volleybolley.core.presentation.base.UiEffect

sealed interface JoinIndividualScreenEffect : UiEffect {
    data object NavigateBack : JoinIndividualScreenEffect
}
