package cy.volleybolley.core.presentation.ui.screens.authorization.authorization

import cy.volleybolley.auth.data.AuthRepositoryImpl.Companion.TAG
import cy.volleybolley.auth.domain.api.usecase.GoogleTokenAuthUseCase
import cy.volleybolley.auth.domain.api.usecase.SaveAccessTokenUseCase
import cy.volleybolley.auth.domain.api.usecase.SavePersonalDataUseCase
import cy.volleybolley.auth.domain.api.usecase.SaveRefreshTokenTimestampUseCase
import cy.volleybolley.auth.domain.api.usecase.SaveRefreshTokenUseCase
import cy.volleybolley.core.domain.model.onFailure
import cy.volleybolley.core.domain.model.onSuccess
import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.model.VolleyUiUtil.showDebugLog
import cy.volleybolley.core.presentation.ui.screens.authorization.authorization.AuthorizationEffect.LaunchGoogleSignIn
import cy.volleybolley.core.presentation.ui.screens.authorization.authorization.AuthorizationEffect.NavigateToRegistration
import cy.volleybolley.core.presentation.ui.screens.authorization.authorization.AuthorizationEffect.ShowToast
import cy.volleybolley.core.presentation.ui.screens.authorization.authorization.AuthorizationEvent.ContinueWithFacebookClicked
import cy.volleybolley.core.presentation.ui.screens.authorization.authorization.AuthorizationEvent.ContinueWithGoogleClicked
import cy.volleybolley.core.presentation.ui.screens.authorization.authorization.AuthorizationEvent.GoogleSignInCancelled
import cy.volleybolley.core.presentation.ui.screens.authorization.authorization.AuthorizationEvent.GoogleSignInFailed
import cy.volleybolley.core.presentation.ui.screens.authorization.authorization.AuthorizationEvent.GoogleTokenReceived
import cy.volleybolley.profile.domain.model.PersonalData
import kotlinx.coroutines.flow.update
import kotlinx.serialization.json.Json

class AuthorizationViewModel(
    private val googleTokenAuthUseCase: GoogleTokenAuthUseCase,
    private val saveAccessTokenUseCase: SaveAccessTokenUseCase,
    private val saveRefreshTokenUseCase: SaveRefreshTokenUseCase,
    private val saveRefreshTokenTimestampUseCase: SaveRefreshTokenTimestampUseCase,
    private val savePersonalDataUseCase: SavePersonalDataUseCase
) : BaseViewModel<AuthorizationState, AuthorizationEvent, AuthorizationEffect>(
    AuthorizationState()
) {
    override val tag: String = "AuthorizationViewModel"

    override fun obtainEvent(event: AuthorizationEvent) {
        when (event) {
            ContinueWithGoogleClicked -> {
                sendUiEffect(LaunchGoogleSignIn)
            }

            is GoogleTokenReceived -> {
                val token = event.idToken
                if (token == null) {
                    sendUiEffect(ShowToast(message = "Couldn't get authorization token"))
                    return
                }

                launchSafe(
                    block = {
                        uiStateMutable.update { it.copy(isLoading = true) }
                        val result = googleTokenAuthUseCase.loginWithGoogle(token)
                        uiStateMutable.update { it.copy(isLoading = false) }

                        result.onSuccess { loginData ->
                            val user = loginData.userPersonalData
                            val accessToken = loginData.accessToken
                            val refreshToken = loginData.refreshToken

                            showUserDataLog(user)

                            saveRefreshTokenUseCase.execute(refreshToken)
                            saveAccessTokenUseCase.execute(accessToken)
                            saveRefreshTokenTimestampUseCase.execute(System.currentTimeMillis())
                            savePersonalDataUseCase.execute(user)

                            val userJson = Json.encodeToString(user)
                            sendUiEffect(NavigateToRegistration(userJson))

                        }.onFailure { error ->
                            sendUiEffect(ShowToast(message = "Authorization error: $error"))
                        }
                    },
                    onError = {
                        uiStateMutable.update { it.copy(isLoading = false) }
                        sendUiEffect(ShowToast(message = "Unexpected error during authorization"))
                    },
                    getErrorLogMessage = { "GoogleTokenReceived: unexpected error -> ${it.message}" }
                )
            }

            GoogleSignInCancelled -> { /* user cancel auth - do nothing */ }

            GoogleSignInFailed -> {
                sendUiEffect(ShowToast(message = "Google Sign-In error"))
            }

            is ContinueWithFacebookClicked -> {
                // Handle Facebook Auth
            }
        }
    }

    private fun showUserDataLog(user: PersonalData) {
        showDebugLog(TAG, "USER section start =========================")
        showDebugLog(TAG, "name = ${user.firstName}")
        showDebugLog(TAG, "lastName = ${user.lastName}")
        showDebugLog(TAG, "avatar = ${user.avatar}")
        showDebugLog(TAG, "gender = ${user.gender}")
        showDebugLog(TAG, "level = ${user.level}")
        showDebugLog(TAG, "dateOfBirth = ${user.birthDate}")
        showDebugLog(TAG, "country = ${user.countryId}")
        showDebugLog(TAG, "city = ${user.cityId}")
        showDebugLog(TAG, "USER section end =========================")
    }
}
