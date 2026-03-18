package cy.volleybolley.core.presentation.ui.screens.authorization.launch

import cy.volleybolley.auth.domain.api.usecase.GetIsRegisteredUseCase
import cy.volleybolley.auth.domain.api.usecase.GetRefreshTokenUseCase
import cy.volleybolley.core.presentation.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update

class LaunchViewModel(
    private val getRefreshTokenUseCase: GetRefreshTokenUseCase,
    private val getIsRegisteredUseCase: GetIsRegisteredUseCase
) : BaseViewModel<LaunchScreenState, LaunchScreenEvent, LaunchScreenEffect>(
    initialState = LaunchScreenState()
) {
    init {
        launchSafe(
            getErrorLogMessage = { throwable ->
                "LaunchViewModel init block: ${throwable.message}"
            }
        ) {
            delay(LAUNCH_DELAY_MS)
            val refreshToken = getRefreshTokenUseCase.execute()

            if (refreshToken != null) {
                val isRegistered = getIsRegisteredUseCase.execute()

                if (isRegistered) {
                    sendUiEffect(LaunchScreenEffect.NavigateToHome)
                } else {
                    sendUiEffect(LaunchScreenEffect.NavigateToOnboarding)
                }
            } else {
                sendUiEffect(LaunchScreenEffect.NavigateToOnboarding)
            }

            uiStateMutable.update { it.copy(isLoading = false) }
        }
    }

    override fun obtainEvent(event: LaunchScreenEvent) {
        // No events
    }

    private companion object {
        const val LAUNCH_DELAY_MS = 2_000L
    }
}
