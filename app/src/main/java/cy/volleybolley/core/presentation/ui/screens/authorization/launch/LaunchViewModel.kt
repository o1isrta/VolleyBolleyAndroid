package cy.volleybolley.core.presentation.ui.screens.authorization.launch

import cy.volleybolley.auth.domain.api.usecase.ClearAllLoginDataUseCase
import cy.volleybolley.auth.domain.api.usecase.GetRefreshTokenUseCase
import cy.volleybolley.auth.domain.api.usecase.RefreshAccessTokenUseCase
import cy.volleybolley.core.domain.model.onFailure
import cy.volleybolley.core.domain.model.onSuccess
import cy.volleybolley.core.presentation.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update

class LaunchViewModel(
    private val getRefreshTokenUseCase: GetRefreshTokenUseCase,
    private val refreshAccessTokenUseCase: RefreshAccessTokenUseCase,
    private val clearAllLoginDataUseCase: ClearAllLoginDataUseCase
) : BaseViewModel<LaunchScreenState, LaunchScreenEvent, LaunchScreenEffect>(
    initialState = LaunchScreenState()
) {
    override val tag: String = "LaunchViewModel"

    init {
        launchSafe(
            getErrorLogMessage = { throwable ->
                "LaunchViewModel init block: ${throwable.message}"
            }
        ) {
            delay(LAUNCH_DELAY_MS)
            val refreshToken = getRefreshTokenUseCase.execute()

            if (refreshToken != null) {
                refreshAccessTokenUseCase.execute()
                    .onSuccess {
                        sendUiEffect(LaunchScreenEffect.NavigateToHome)
                    }
                    .onFailure {
                        clearAllLoginDataUseCase.execute()
                        sendUiEffect(LaunchScreenEffect.NavigateToAuthorization)
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
