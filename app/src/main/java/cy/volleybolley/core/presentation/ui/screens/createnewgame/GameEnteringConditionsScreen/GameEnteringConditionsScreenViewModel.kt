package cy.volleybolley.core.presentation.ui.screens.createnewgame.GameEnteringConditionsScreen

import androidx.lifecycle.viewModelScope
import cy.volleybolley.core.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class GameEnteringConditionsScreenViewModel :
    BaseViewModel<GameEnteringConditionsScreenState, GameEnteringConditionsScreenEvent, GameEnteringConditionsScreenEffect>(
        GameEnteringConditionsScreenState()
    ) {

        override val tag: String = "AboutLevelsViewModel"

        override fun obtainEvent(event: GameEnteringConditionsScreenEvent) {
            when (event) {
                GameEnteringConditionsScreenEvent.OnBackClicked -> {
                    viewModelScope.launch {
                        sendUiEffect(GameEnteringConditionsScreenEffect.NavigateBack)
                    }
                }
            }
        }

    /*private val _screenState = MutableStateFlow<GameEnteringConditionsScreenState>(GameEnteringConditionsScreenState.Content())
    val screenState: StateFlow<GameEnteringConditionsScreenState> = _screenState

    //private val _eventChannel = Channel<Event>()
    //val event: kotlinx.coroutines.flow.Flow<Event> = _eventChannel.receiveAsFlow()

    fun onPrivacySelected(privacy: Privacy) {
        val currentState = _screenState.value
        if (currentState is GameEnteringConditionsScreenState.Content) {
            _screenState.value = currentState.copy(selectedPrivacy = privacy)
        }
        //_screenState.value = _screenState.value.copy(selectedPrivacy = privacy)
    }

    fun onAddPaymentClicked() {
        val currentState = _screenState.value
        if (currentState is GameEnteringConditionsScreenState.Content) {
            _screenState.value = currentState.copy(accountState = AccountState.Loading)
            viewModelScope.launch {
                delay(2000) // Имитация создания аккаунта
                val  accountNumber = generateAccountNumber() // Generate random account number
                _screenState.value = currentState.copy(accountState = AccountState.Linked(accountNumber))
            }
        }

       *//* _screenState.value = _screenState.value.copy(accountState = AccountState.Loading)

        viewModelScope.launch {
            delay(2000)

            _screenState.value = _screenState.value.copy(accountState = AccountState.Linked("New Account Number"))
            _eventChannel.send(Event.NavigateToPaymentsScreen("New Account Number"))
        }*//*
    }

    // имитация создания аккаунта
    private fun generateAccountNumber():String {
        return Random.nextInt(100000,  999999).toString()
    }*/

/*    sealed class Event {
        // data class ShowToast(val message: String) : Event()
        data class NavigateToPaymentsScreen(val accountNumber: String) : Event()
    }*/
}
