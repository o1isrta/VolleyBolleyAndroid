package cy.volleybolley.core.presentation.ui.screens.authorization.authorization

import androidx.lifecycle.viewModelScope
import cy.volleybolley.auth.domain.api.usecase.AuthUseCase
import cy.volleybolley.auth.domain.api.usecase.SavePersonalDataUseCase
import cy.volleybolley.auth.domain.api.usecase.SaveTokensUseCase
import cy.volleybolley.core.domain.model.onFailure
import cy.volleybolley.core.domain.model.onSuccess
import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.screens.authorization.authorization.AuthorizationEffect.LaunchGoogleSignIn
import cy.volleybolley.core.presentation.ui.screens.authorization.authorization.AuthorizationEffect.NavigateToRegistration
import cy.volleybolley.core.presentation.ui.screens.authorization.authorization.AuthorizationEffect.ShowToast
import cy.volleybolley.core.presentation.ui.screens.authorization.authorization.AuthorizationEvent.ContinueWithFacebookClicked
import cy.volleybolley.core.presentation.ui.screens.authorization.authorization.AuthorizationEvent.ContinueWithGoogleClicked
import cy.volleybolley.core.presentation.ui.screens.authorization.authorization.AuthorizationEvent.GoogleSignInCancelled
import cy.volleybolley.core.presentation.ui.screens.authorization.authorization.AuthorizationEvent.GoogleSignInFailed
import cy.volleybolley.core.presentation.ui.screens.authorization.authorization.AuthorizationEvent.GoogleTokenReceived
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class AuthorizationViewModel(
    private val authUseCase: AuthUseCase,
    private val saveTokensUseCase: SaveTokensUseCase,
    private val savePersonalDataUseCase: SavePersonalDataUseCase
) : BaseViewModel<AuthorizationState, AuthorizationEvent, AuthorizationEffect>(
    AuthorizationState()
) {

    override val tag: String = "AuthorizationViewModel"

    override fun obtainEvent(event: AuthorizationEvent) {
        when (event) {
            ContinueWithGoogleClicked -> {
                viewModelScope.launch {
                    sendUiEffect(LaunchGoogleSignIn)
                }
            }

            is GoogleTokenReceived -> {
                event.idToken?.let { token ->
                    viewModelScope.launch {
                        uiStateMutable.update { it.copy(isLoading = true) }
                        val result = authUseCase.loginWithGoogle(token)
                        uiStateMutable.update { it.copy(isLoading = false) }

                        result.onSuccess { loginData ->
                            val user = loginData.userPersonalData
                            val accessToken = loginData.accessToken
                            val refreshToken = loginData.refreshToken

                            saveTokensUseCase.execute(accessToken, refreshToken)
                            savePersonalDataUseCase.execute(user)

                            val userJson = Json.encodeToString(user)
                            sendUiEffect(NavigateToRegistration(userJson))

                        }.onFailure { error ->
                            sendUiEffect(
                                ShowToast(
                                    message = "Authorization error: $error"
                                )
                            )
                        }
                    }
                } ?: run {
                    viewModelScope.launch {
                        sendUiEffect(
                            ShowToast(
                                message = "Couldn't get authorization token"
                            )
                        )
                    }
                }
            }

            GoogleSignInCancelled -> { /* user cancel auth - do nothing */ }

            GoogleSignInFailed -> {
                viewModelScope.launch {
                    sendUiEffect(
                        ShowToast(
                            message = "Google Sign-In error"
                        )
                    )
                }
            }

            is ContinueWithFacebookClicked -> {
                // Handle Facebook Auth
            }
        }
    }
}
