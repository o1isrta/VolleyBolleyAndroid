package cy.volleybolley.core.presentation.ui.screens.authorization.authorization

import android.content.Intent
import android.util.Log
import androidx.lifecycle.viewModelScope
import cy.volleybolley.auth.domain.AuthInteractor
import cy.volleybolley.auth.domain.TokensInteractor
import cy.volleybolley.auth.ui.GoogleSignInHelper
import cy.volleybolley.core.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class AuthorizationViewModel(
    private val googleSignInHelper: GoogleSignInHelper,
    private val authInteractor: AuthInteractor,
    private val tokensInteractor: TokensInteractor
) :
    BaseViewModel<AuthorizationState, AuthorizationEvent, AuthorizationEffect>(
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
                        val response = authInteractor.loginWithGoogle(token)
                        uiStateMutable.update { it.copy(isLoading = false) }

                        if (response.isSuccess) {
                            Log.d(tag, response.body?.player.toString())
                            val user = response.body?.player
                            if (user != null) {
                                val userJson = Json.encodeToString(user)
                                val accessToken = response.body?.accessToken
                                val refreshToken = response.body?.refreshToken
                                if (accessToken != null && refreshToken != null) {
                                    tokensInteractor.saveTokens(accessToken, refreshToken)
                                    val token = tokensInteractor.getAccessToken()
                                    Log.d("AccessToken", token.toString())
                                } else {
                                    sendUiEffect(AuthorizationEffect.ShowError("Ошибка: не удалось сохранить токены"))
                                }
                                sendUiEffect(AuthorizationEffect.NavigateToRegistration(userJson))
                            } else {
                                sendUiEffect(
                                    AuthorizationEffect.ShowError("Ошибка: пользователь не найден в ответе сервера")
                                )
                            }
                        } else {
                            sendUiEffect(
                                AuthorizationEffect.ShowError("Ошибка авторизации: ${response.resultCode.code}")
                            )
                        }
                    }
                } ?: run {
                    viewModelScope.launch {
                        sendUiEffect(AuthorizationEffect.ShowError("Не удалось получить токен"))
                    }
                }
            }

            AuthorizationEvent.ContinueWithFacebookClicked -> {
                /*viewModelScope.launch {
                    sendUiEffect(AuthorizationEffect.NavigateToRegistration)
                }*/
            }
        }
    }

    fun extractGoogleIdToken(intent: Intent?): String? =
        googleSignInHelper.extractIdToken(intent)
}
