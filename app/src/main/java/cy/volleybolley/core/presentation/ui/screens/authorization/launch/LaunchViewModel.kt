package cy.volleybolley.core.presentation.ui.screens.authorization.launch

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import cy.volleybolley.core.presentation.base.BaseViewModel

class LaunchViewModel : BaseViewModel<LaunchState, LaunchEvent, LaunchEffect>(LaunchState()) {
    override val tag: String = "LaunchViewModel"
    private val launchDelay = 3_000L

    init {
        viewModelScope.launch {
            delay(launchDelay)
            sendUiEffect(LaunchEffect.NavigateToOnboarding)
        }
    }

    override fun obtainEvent(event: LaunchEvent) {
        when (event) {
            LaunchEvent.Start -> viewModelScope.launch { sendUiEffect(LaunchEffect.NavigateToOnboarding) }
        }
    }
}

