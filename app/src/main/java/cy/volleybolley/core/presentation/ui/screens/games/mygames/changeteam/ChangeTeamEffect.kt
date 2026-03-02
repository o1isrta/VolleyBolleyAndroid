package cy.volleybolley.core.presentation.ui.screens.games.mygames.changeteam

import cy.volleybolley.core.presentation.base.UiEffect

sealed interface ChangeTeamEffect : UiEffect {
    data object NavigateBack : ChangeTeamEffect
    data object TeamSelected : ChangeTeamEffect
}
