package cy.volleybolley.core.presentation.ui.screens.authorization.launch

import androidx.lifecycle.viewModelScope
import cy.volleybolley.core.presentation.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val LAUNCH_DELAY_MS = 3_000L

class LaunchViewModel :
    BaseViewModel<LaunchState, LaunchEvent, LaunchEffect>(
        LaunchState()
    ) {

    override val tag: String = "LaunchViewModel"

    init {
        viewModelScope.launch {
            delay(LAUNCH_DELAY_MS)
            sendUiEffect(LaunchEffect.NavigateToOnboarding)
        }
    }

    override fun obtainEvent(event: LaunchEvent) {
        when (event) {
            LaunchEvent.Start -> {
                viewModelScope.launch {
                    sendUiEffect(LaunchEffect.NavigateToOnboarding)
                }
            }
        }
    }
}
