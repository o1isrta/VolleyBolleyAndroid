package cy.volleybolley.auth.ui.screens.authorization

import cy.volleybolley.auth.domain.api.usecase.GoogleTokenAuthUseCase
import cy.volleybolley.auth.ui.screens.authorization.AuthorizationEffect.NavigateToHome
import cy.volleybolley.auth.ui.screens.authorization.AuthorizationEffect.NavigateToRegistration
import cy.volleybolley.auth.ui.screens.authorization.AuthorizationEffect.ShowToast
import cy.volleybolley.auth.ui.screens.authorization.AuthorizationEvent.GoogleTokenReceived
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.core.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.update

class AuthorizationViewModel(
    private val googleTokenAuthUseCase: GoogleTokenAuthUseCase
) : BaseViewModel<AuthorizationState, AuthorizationEvent, AuthorizationEffect>(
    AuthorizationState()
) {
    override fun obtainEvent(event: AuthorizationEvent) {
        when (event) {
            is GoogleTokenReceived -> onGoogleTokenReceived(event.googleIdToken)
            AuthorizationEvent.GoogleSignInStarted -> onGoogleSignInStarted()
            AuthorizationEvent.GoogleSignInFailed -> onGoogleSignInFailed()
            AuthorizationEvent.ContinueWithFacebookClicked -> { /* Handle Facebook Auth */ }
        }
    }

    private fun onGoogleTokenReceived(googleIdToken: String) {
        launchSafe(
            block = {
                when (val result = googleTokenAuthUseCase.execute(googleIdToken)) {
                    is VolleyResult.Success -> {
                        uiStateMutable.update { it.copy(isGoogleLoading = false) }
                        if (result.data.isRegistered) {
                            sendUiEffect(NavigateToHome)
                        } else {
                            sendUiEffect(NavigateToRegistration)
                        }
                    }
                    is VolleyResult.Failure -> {
                        uiStateMutable.update { it.copy(isGoogleLoading = false) }
                        sendUiEffect(ShowToast(message = "Authorization error: ${result.error}"))
                    }
                }
            },
            onError = {
                uiStateMutable.update { it.copy(isGoogleLoading = false) }
                sendUiEffect(ShowToast(message = "Unexpected error during authorization"))
            },
            getErrorLogMessage = { "GoogleTokenReceived: unexpected error -> ${it.message}" }
        )
    }

    private fun onGoogleSignInStarted() {
        uiStateMutable.update { it.copy(isGoogleLoading = true) }
    }

    private fun onGoogleSignInFailed() {
        uiStateMutable.update { it.copy(isGoogleLoading = false) }
    }
}
