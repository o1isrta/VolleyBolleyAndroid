package cy.volleybolley.core.presentation.ui.screens.createnewgame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class GameEnteringConditionsScreenViewModel : ViewModel() {
    private val _screenState = MutableStateFlow(GameEnteringConditionsScreenState())
    val screenState: StateFlow<GameEnteringConditionsScreenState> = _screenState

    private val _eventChannel = Channel<Event>()
    val event: kotlinx.coroutines.flow.Flow<Event> = _eventChannel.receiveAsFlow()

    fun onPrivacySelected(gender: Privacy) {
        _screenState.value = _screenState.value.copy(selectedPrivacy = gender)
    }

    fun onCreateAccountClicked() {
        _screenState.value = _screenState.value.copy(accountState = AccountState.Loading)

        viewModelScope.launch {
            delay(2000)

            _screenState.value = _screenState.value.copy(accountState = AccountState.Linked("New Account Number"))
            _eventChannel.send(Event.NavigateToPrivacyOptionsScreen("New Account Number"))
        }
    }

}
