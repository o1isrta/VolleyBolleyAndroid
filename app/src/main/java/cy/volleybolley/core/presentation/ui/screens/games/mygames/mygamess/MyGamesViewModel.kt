package cy.volleybolley.core.presentation.ui.screens.games.mygames.mygamess


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cy.volleybolley.core.presentation.ui.navigation.BasicGameSetupRoute
import cy.volleybolley.core.presentation.ui.navigation.MyGameRoute
import cy.volleybolley.core.presentation.ui.navigation.MyTourneyRoute
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Locale

class MyGamesViewModel : ViewModel() {

    private val _state = MutableStateFlow(MyGamesState()) // заглушка
    val state = _state.asStateFlow()

    private val _effects = MutableSharedFlow<MyGamesEffect>()
    val effects = _effects.asSharedFlow()

    fun dispatch(action: MyGamesAction) {
        when (action) {
            MyGamesAction.ClickBack -> emit(MyGamesEffect.NavigateBack)

            MyGamesAction.ClickCreateGame -> emit(MyGamesEffect.Navigate(BasicGameSetupRoute))

            is MyGamesAction.ClickDetails -> {
                val route = when (action.details.gameType.uppercase(Locale.ROOT)) {
                    "GAME" -> MyGameRoute
                    "TOURNAMENT" -> MyTourneyRoute
                    else -> MyGameRoute
                }
                emit(MyGamesEffect.Navigate(route))
            }

            is MyGamesAction.ClickMap -> emit(MyGamesEffect.OpenMap(action.location))

            MyGamesAction.Refresh -> {
                // TODO: подтянуть из домейна список игр и hasGames
            }
        }
    }

    private fun emit(effect: MyGamesEffect) {
        viewModelScope.launch { _effects.emit(effect) }
    }
}
