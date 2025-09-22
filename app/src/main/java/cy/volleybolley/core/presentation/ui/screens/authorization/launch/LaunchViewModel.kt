package cy.volleybolley.core.presentation.ui.screens.authorization.launch

import androidx.lifecycle.viewModelScope
import cy.volleybolley.core.presentation.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LaunchViewModel : BaseViewModel<LaunchState, LaunchEvent, LaunchEffect>(
    initialState = LaunchState()
) {
    override val tag: String = "LaunchViewModel"

    init {
        viewModelScope.launch {
            uiStateMutable.update { it.copy(isLoading = true) }
            delay(LAUNCH_DELAY_MS)
            sendUiEffect(LaunchEffect.NavigateToOnboarding)
            uiStateMutable.update { it.copy(isLoading = false) }
        }
    }

    override fun obtainEvent(event: LaunchEvent) {
        // No events
    }

    private companion object {
        const val LAUNCH_DELAY_MS = 3_000L
    }
}
