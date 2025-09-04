package cy.volleybolley.core.presentation.ui.screens.games.mygames.mygame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyGameViewModel : ViewModel() {

    private val _state = MutableStateFlow(MyGameState()) // заглушка
    val state = _state.asStateFlow()

    private val _effects = MutableSharedFlow<MyGameEffect>()
    val effects = _effects.asSharedFlow()

    fun dispatch(action: MyGameAction) {
        when (action) {
            MyGameAction.ClickBack -> emit(MyGameEffect.NavigateBack)
            is MyGameAction.ClickMap -> emit(MyGameEffect.OpenMap(action.location))

            // Заглушки
            MyGameAction.ClickInvite -> emit(MyGameEffect.InvitePlayers)
            MyGameAction.ClickShare -> emit(MyGameEffect.ShareLink)
            MyGameAction.ClickCancel -> emit(MyGameEffect.CancelGame)

            is MyGameAction.DeletePlayer -> {
                // TODO: домейн-удаление игрока, затем обновить state
            }

            MyGameAction.Refresh -> {
                // TODO: подтянуть данные из домейна
            }
        }
    }

    private fun emit(effect: MyGameEffect) {
        viewModelScope.launch { _effects.emit(effect) }
    }
}
