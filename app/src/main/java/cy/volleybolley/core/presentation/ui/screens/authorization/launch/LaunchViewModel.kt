package cy.volleybolley.core.presentation.ui.screens.authorization.launch

import cy.volleybolley.R
import cy.volleybolley.auth.domain.api.usecase.GetIsRegisteredUseCase
import cy.volleybolley.auth.domain.api.usecase.GetRefreshTokenUseCase
import cy.volleybolley.core.domain.model.onFailure
import cy.volleybolley.core.domain.model.onSuccess
import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.referencedata.domain.api.GetCountriesUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update

class LaunchViewModel(
    private val getRefreshTokenUseCase: GetRefreshTokenUseCase,
    private val getIsRegisteredUseCase: GetIsRegisteredUseCase,
    private val getCountriesUseCase: GetCountriesUseCase
) : BaseViewModel<LaunchScreenState, LaunchScreenEvent, LaunchScreenEffect>(
    initialState = LaunchScreenState.Loading
) {
    init {
        loadCountries()
    }

    override fun obtainEvent(event: LaunchScreenEvent) {
        when (event) {
            LaunchScreenEvent.RetryClicked -> loadCountries()
        }
    }

    private fun loadCountries() {
        uiStateMutable.update { LaunchScreenState.Loading }

        launchSafe(
            onError = { uiStateMutable.update { LaunchScreenState.Error(R.string.something_went_wrong) } },
            getErrorLogMessage = { throwable ->
                "LaunchViewModel: ${throwable.message}"
            }
        ) {
            getCountriesUseCase.execute()
                .onSuccess {
                    delay(LAUNCH_DELAY_MS)
                    proceedToNextScreen()
                }
                .onFailure {
                    uiStateMutable.update { LaunchScreenState.Error(R.string.something_went_wrong) }
                }
        }
    }

    private suspend fun proceedToNextScreen() {
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
    }

    private companion object {
        const val LAUNCH_DELAY_MS = 2_000L
    }
}
