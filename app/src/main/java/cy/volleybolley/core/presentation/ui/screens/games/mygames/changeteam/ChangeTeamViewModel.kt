package cy.volleybolley.core.presentation.ui.screens.games.mygames.changeteam

import cy.volleybolley.core.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ChangeTeamViewModel : BaseViewModel<ChangeTeamState, ChangeTeamAction, ChangeTeamEffect>(
    initialState = ChangeTeamState()
) {
    private val _state = MutableStateFlow(ChangeTeamState())
    val state = _state.asStateFlow()

    override fun obtainEvent(event: ChangeTeamAction) {
        when (event) {
            is ChangeTeamAction.SelectTeam -> {
                _state.update { it.copy(selectedTeam = event.index) }
            }

            is ChangeTeamAction.RemoveMember -> {
                _state.update { current ->
                    val newTeams = current.teams.toMutableList()
                    val team = newTeams[event.teamIndex]
                    val newMembers = team.members.toMutableList()
                    newMembers[event.memberIndex] = MemberUi(null, null)
                    newTeams[event.teamIndex] = team.copy(members = newMembers)
                    current.copy(teams = newTeams)
                }
            }

            ChangeTeamAction.ConfirmSelection -> {
                // Вызов domain-слоя
            }
        }
    }
}
