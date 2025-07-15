package cy.volleybolley.core.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import android.util.Log

abstract class BaseViewModel<State, Event, UiEffect> : ViewModel() {

    // region Abstract
    protected abstract val initialState: State
    abstract val tag: String
    abstract fun obtainEvent(event: Event)
    // endregion

    // region State
    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    protected fun setState(reducer: State.() -> State) {
        _uiState.value = _uiState.value.reducer()
    }
    // endregion

    // region One-time Events
    private val _effect = Channel<UiEffect>(Channel.BUFFERED)
    val uiEffect: Flow<UiEffect> = _effect.receiveAsFlow()

    protected fun sendUiEffect(effect: UiEffect) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }
    // endregion

    // region Safe Launch
    protected fun launchSafe(
        onError: (Throwable) -> Unit = { Log.e(tag, "Error in launchSafe", it) },
        block: suspend () -> Unit
    ) {
        viewModelScope.launch {
            try {
                block()
            } catch (e: Exception) {
                onError(e)
            }
        }
    }
    // endregion
}
