package cy.volleybolley.core.presentation.ui.screens.games.mygames.changeteam

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ChangeTeamViewModel : ViewModel() {

    private val _state = MutableStateFlow(ChangeTeamState())
    val state = _state.asStateFlow()

    fun dispatch(action: ChangeTeamAction) {
        when (action) {
            is ChangeTeamAction.SelectTeam -> {
                _state.update { it.copy(selectedTeam = action.index) }
            }

            is ChangeTeamAction.RemoveMember -> {
                _state.update { state ->
                    val newTeams = state.teams.toMutableList()
                    val team = newTeams[action.teamIndex]
                    team.members[action.memberIndex] = MemberUi(null, null)
                    state.copy(teams = newTeams)
                }
            }

            is ChangeTeamAction.ConfirmSelection -> {
                // TODO: вызов domain-слоя
            }
        }
    }
}
