package cy.volleybolley.core.presentation.ui.screens.games.mygames.changeteam

sealed interface ChangeTeamAction {
    data class SelectTeam(val index: Int) : ChangeTeamAction
    data class RemoveMember(val teamIndex: Int, val memberIndex: Int) : ChangeTeamAction
    data object ConfirmSelection : ChangeTeamAction
}
