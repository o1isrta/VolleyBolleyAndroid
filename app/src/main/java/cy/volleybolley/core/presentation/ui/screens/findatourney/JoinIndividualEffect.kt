package cy.volleybolley.core.presentation.ui.screens.findatourney

import cy.volleybolley.core.presentation.base.UiEffect

sealed interface JoinIndividualEffect : UiEffect {
    data object NavigateBack : JoinIndividualEffect
}
