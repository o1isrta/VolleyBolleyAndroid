package cy.volleybolley.auth.chooseMethod

import cy.volleybolley.auth.domain.api.usecase.GoogleTokenAuthUseCase
import cy.volleybolley.auth.chooseMethod.model.AuthorizationEffect.NavigateToHome
import cy.volleybolley.auth.chooseMethod.model.AuthorizationEffect.NavigateToRegistration
import cy.volleybolley.auth.chooseMethod.model.AuthorizationEffect.ShowToast
import cy.volleybolley.auth.chooseMethod.model.AuthorizationEvent.GoogleTokenReceived
import cy.volleybolley.auth.chooseMethod.model.AuthorizationEffect
import cy.volleybolley.auth.chooseMethod.model.AuthorizationEvent
import cy.volleybolley.auth.chooseMethod.model.AuthorizationState
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
