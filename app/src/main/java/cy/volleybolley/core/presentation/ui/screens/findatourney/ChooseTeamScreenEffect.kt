package cy.volleybolley.core.presentation.ui.screens.findatourney

import cy.volleybolley.core.presentation.base.UiEffect

sealed interface ChooseTeamScreenEffect : UiEffect {
    data object NavigateBack : ChooseTeamScreenEffect
}
