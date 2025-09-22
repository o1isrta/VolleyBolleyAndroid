package cy.volleybolley.core.presentation.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<State : UiState, Event : UiEvent, Effect : UiEffect>(
    initialState: State
) : ViewModel() {
    abstract val tag: String

    /**
     * Входная точка MVI для событий с ui
     */
    abstract fun obtainEvent(event: Event)

    protected val uiStateMutable = MutableStateFlow(initialState)

    /**
     * Выходная точка MVI для единого состояния всего экрана
     */
    val uiState: StateFlow<State> = uiStateMutable.asStateFlow()

    protected val effectMutable = Channel<Effect?>(Channel.BUFFERED)

    /**
     * Выходная точка (удобный костыль MVI) для одноразовых событий (показать toast или диалог)
     */
    val uiEffect: Flow<Effect?> = effectMutable.receiveAsFlow()

    protected fun sendUiEffect(effect: Effect?) {
        viewModelScope.launch {
            effectMutable.send(effect)
        }
    }

    // В этом случае используем общую ошибку для избегания вылетов при недочетах во внешних зависимостях
    @Suppress("TooGenericExceptionCaught", "InstanceOfCheckForException")
    /**
     * Безопасный вызов внешних зависимостией (например usecase) для user-friendly
     * @param onError блок обработки неожиданной ошибки от внешней зависимости
     */
    protected fun launchSafe(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        onError: (suspend (Throwable) -> Unit)? = null,
        getErrorLogMessage: (Throwable) -> String,
        block: suspend () -> Unit
    ) {
        viewModelScope.launch(dispatcher) {
            try {
                block()
            } catch (e: Exception) {
                if (e is CancellationException) {
                    throw CancellationException()
                }
                Log.e(tag, getErrorLogMessage(e), e)
                onError?.invoke(e)
            }
        }
    }
}
