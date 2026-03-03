package cy.volleybolley.core.presentation.ui.screens.findatourney

import cy.volleybolley.core.presentation.base.UiEffect

sealed interface InvitePlayersScreenEffect : UiEffect {
    data object NavigateBack : InvitePlayersScreenEffect
}
