package cy.volleybolley.core.presentation.ui.screens.games.mygames.mytourney

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cy.volleybolley.core.presentation.ui.navigation.ChangeTeamRoute
import cy.volleybolley.core.presentation.ui.navigation.ManagePlayersRoute
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyTourneyViewModel : ViewModel() {

    private val _state = MutableStateFlow(MyTourneyState()) // заглушка
    val state = _state.asStateFlow()

    private val _effects = MutableSharedFlow<MyTourneyEffect>()
    val effects = _effects.asSharedFlow()

    fun dispatch(action: MyTourneyAction) {
        when (action) {
            MyTourneyAction.ClickBack -> emit(MyTourneyEffect.NavigateBack)
            is MyTourneyAction.ClickMap -> emit(MyTourneyEffect.OpenMap(action.location))

            MyTourneyAction.ClickManagePlayers -> emit(MyTourneyEffect.Navigate(ManagePlayersRoute))
            MyTourneyAction.ClickChangeTeam -> emit(MyTourneyEffect.Navigate(ChangeTeamRoute))

            // заглушки
            MyTourneyAction.ClickInvite -> emit(MyTourneyEffect.InvitePlayers)
            MyTourneyAction.ClickShare -> emit(MyTourneyEffect.ShareLink)
            MyTourneyAction.ClickCancel -> emit(MyTourneyEffect.CancelEvent)

            MyTourneyAction.Refresh -> {
                // TODO: подтянуть из домейна детали турнира и обновить _state
            }
        }
    }

    private fun emit(effect: MyTourneyEffect) {
        viewModelScope.launch { _effects.emit(effect) }
    }
}
