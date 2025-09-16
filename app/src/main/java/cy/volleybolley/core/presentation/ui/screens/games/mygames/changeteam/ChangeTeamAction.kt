package cy.volleybolley.core.presentation.ui.screens.games.mygames.changeteam

import cy.volleybolley.core.presentation.base.UiEvent

sealed interface ChangeTeamAction : UiEvent {
    data class SelectTeam(val index: Int) : ChangeTeamAction
    data class RemoveMember(val teamIndex: Int, val memberIndex: Int) : ChangeTeamAction
    data object ConfirmSelection : ChangeTeamAction
}
