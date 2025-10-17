package cy.volleybolley.core.presentation.ui.screens.authorization.authorization

import android.content.Intent
import androidx.lifecycle.viewModelScope
import cy.volleybolley.auth.domain.AuthUseCase
import cy.volleybolley.auth.domain.TokensInteractor
import cy.volleybolley.auth.ui.GoogleSignInHelper
import cy.volleybolley.core.domain.model.onFailure
import cy.volleybolley.core.domain.model.onSuccess
import cy.volleybolley.core.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class AuthorizationViewModel(
    private val googleSignInHelper: GoogleSignInHelper,
    private val authUseCase: AuthUseCase,
    private val tokensInteractor: TokensInteractor
) : BaseViewModel<AuthorizationState, AuthorizationEvent, AuthorizationEffect>(
    AuthorizationState()
) {

    override val tag: String = "AuthorizationViewModel"

    override fun obtainEvent(event: AuthorizationEvent) {
        when (event) {
            AuthorizationEvent.ContinueWithGoogleClicked -> {
                viewModelScope.launch {
                    val intentSender = googleSignInHelper.launch()
                    if (intentSender != null) {
                        sendUiEffect(AuthorizationEffect.LaunchGoogleSignIn(intentSender))
                    } else {
                        sendUiEffect(AuthorizationEffect.ShowError("Не удалось запустить Google Sign-in"))
                    }
                }
            }

            is AuthorizationEvent.GoogleTokenReceived -> {
                event.idToken?.let { token ->
                    viewModelScope.launch {
                        uiStateMutable.update { it.copy(isLoading = true) }
                        val result = authUseCase.loginWithGoogle(token)
                        uiStateMutable.update { it.copy(isLoading = false) }
                        // -----------------------------
                        result.onSuccess { loginData ->
                            val user = loginData.userPersonalData
                            val accessToken = loginData.accessToken
                            val refreshToken = loginData.refreshToken

                            val userJson = Json.encodeToString(user)
                            tokensInteractor.saveTokens(accessToken, refreshToken)
                            sendUiEffect(AuthorizationEffect.NavigateToRegistration(userJson))

                        }.onFailure { error ->
                            sendUiEffect(AuthorizationEffect.ShowError("Ошибка авторизации: $error"))
                        }
                    }
                }
            }

            is AuthorizationEvent.ContinueWithFacebookClicked -> {
                // Тут должна быть и может даже будет авторизация через Facebook
            }
        }
    }

    fun extractGoogleIdToken(intent: Intent?): String? = googleSignInHelper.extractIdToken(intent)
}



