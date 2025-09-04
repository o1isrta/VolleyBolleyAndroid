package cy.volleybolley.core.presentation.ui.screens.games.mygames.manageplayers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ManagePlayersViewModel : ViewModel() {

    private val _state = MutableStateFlow(ManagePlayersState())
    val state = _state.asStateFlow()

    private val _effects = MutableSharedFlow<ManagePlayersEffect>()
    val effects = _effects.asSharedFlow()

    fun dispatch(action: ManagePlayersAction) {
        when (action) {
            ManagePlayersAction.ClickBack -> navigateBack()
            is ManagePlayersAction.RemovePlayer -> {
                // TODO: use-case удаления
            }

            ManagePlayersAction.Refresh -> {
                // TODO: подтянуть актуальный список из домейна
            }
        }
    }

    private fun navigateBack() {
        viewModelScope.launch {
            _effects.emit(ManagePlayersEffect.NavigateBack)
        }
    }
}
