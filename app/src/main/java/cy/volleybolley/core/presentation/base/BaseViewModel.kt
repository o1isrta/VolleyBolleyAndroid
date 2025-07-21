package cy.volleybolley.core.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

abstract class BaseViewModel<State : UiState, Event : UiEvent, Effect : UiEffect>(
    initialState: State
) : ViewModel() {

    // region Abstract
    abstract val tag: String
    abstract fun obtainEvent(event: Event)
    // endregion

    // region State
    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    // endregion

    // region One-time Events
    private val _effect = Channel<Effect>(Channel.BUFFERED)
    val uiEffect: Flow<Effect> = _effect.receiveAsFlow()

    protected fun sendUiEffect(effect: Effect) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }
    // endregion

    // region Safe Launch
    protected fun launchSafe(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        onError: suspend (Throwable) -> Unit = { Log.e(tag, "Error in launchSafe", it) },
        block: suspend () -> Unit
    ) {
        viewModelScope.launch(dispatcher) {
            try {
                block()
            } catch (e: Exception) {
                onError(e)
            }
        }
    }
    // endregion
}
