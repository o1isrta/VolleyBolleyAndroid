package cy.volleybolley.core.presentation.ui.screens.games.mygames.gamehome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cy.volleybolley.core.presentation.ui.navigation.ArchiveRoute
import cy.volleybolley.core.presentation.ui.navigation.GameInvitesRoute
import cy.volleybolley.core.presentation.ui.navigation.MyGamesRoute
import cy.volleybolley.core.presentation.ui.navigation.UpcomingGamesRoute
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GameHomeViewModel : ViewModel() {

    private val _state = MutableStateFlow(GameHomeState()) // заглушка
    val state = _state.asStateFlow()

    private val _effects = MutableSharedFlow<GameHomeEffect>()
    val effects = _effects.asSharedFlow()

    fun dispatch(action: GameHomeAction) {
        when (action) {
            GameHomeAction.ClickMyGames -> navigate(MyGamesRoute)
            GameHomeAction.ClickUpcomingGames -> navigate(UpcomingGamesRoute)
            GameHomeAction.ClickInvites -> navigate(GameInvitesRoute)
            GameHomeAction.ClickArchive -> navigate(ArchiveRoute)
            GameHomeAction.Refresh -> {
                // TODO: заменить на вызов домейна
            }
        }
    }

    private fun navigate(route: Any) {
        viewModelScope.launch {
            _effects.emit(GameHomeEffect.Navigate(route))
        }
    }
}
