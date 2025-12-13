package cy.volleybolley.auth.ui.screens.authorization

import cy.volleybolley.auth.data.AuthRepositoryImpl
import cy.volleybolley.auth.domain.api.usecase.GoogleTokenAuthUseCase
import cy.volleybolley.auth.domain.api.usecase.SaveAccessTokenUseCase
import cy.volleybolley.auth.domain.api.usecase.SaveIsRegisteredUseCase
import cy.volleybolley.auth.domain.api.usecase.SavePersonalDataUseCase
import cy.volleybolley.auth.domain.api.usecase.SaveRefreshTokenTimestampUseCase
import cy.volleybolley.auth.domain.api.usecase.SaveRefreshTokenUseCase
import cy.volleybolley.auth.domain.models.LoginData
import cy.volleybolley.auth.ui.screens.authorization.AuthorizationEffect.LaunchGoogleSignIn
import cy.volleybolley.auth.ui.screens.authorization.AuthorizationEffect.NavigateToHome
import cy.volleybolley.auth.ui.screens.authorization.AuthorizationEffect.NavigateToRegistration
import cy.volleybolley.auth.ui.screens.authorization.AuthorizationEffect.ShowToast
import cy.volleybolley.auth.ui.screens.authorization.AuthorizationEvent.ContinueWithGoogleClicked
import cy.volleybolley.auth.ui.screens.authorization.AuthorizationEvent.GoogleTokenReceived
import cy.volleybolley.core.domain.model.onFailure
import cy.volleybolley.core.domain.model.onSuccess
import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.model.VolleyUiUtil
import cy.volleybolley.profile.domain.model.PersonalData
import kotlinx.coroutines.flow.update
import kotlinx.serialization.json.Json

class AuthorizationViewModel(
    private val googleTokenAuthUseCase: GoogleTokenAuthUseCase,
    private val saveAccessTokenUseCase: SaveAccessTokenUseCase,
    private val saveRefreshTokenUseCase: SaveRefreshTokenUseCase,
    private val saveRefreshTokenTimestampUseCase: SaveRefreshTokenTimestampUseCase,
    private val savePersonalDataUseCase: SavePersonalDataUseCase,
    private val saveIsRegisteredUseCase: SaveIsRegisteredUseCase
) : BaseViewModel<AuthorizationState, AuthorizationEvent, AuthorizationEffect>(
    AuthorizationState()
) {
    override val tag: String = "AuthorizationViewModel"

    override fun obtainEvent(event: AuthorizationEvent) {
        when (event) {
            ContinueWithGoogleClicked -> sendUiEffect(LaunchGoogleSignIn)
            is GoogleTokenReceived -> onGoogleTokenReceived(event.googleIdToken)
            AuthorizationEvent.GoogleSignInCancelled -> { /* user cancel auth - do nothing */
            }

            AuthorizationEvent.GoogleSignInFailed -> sendUiEffect(ShowToast(message = "Google Sign-In error"))
            is AuthorizationEvent.ContinueWithFacebookClicked -> { /* Handle Facebook Auth */
            }
        }
    }

    private fun onGoogleTokenReceived(googleIdToken: String?) {
        if (googleIdToken == null) {
            sendUiEffect(ShowToast(message = "Couldn't get authorization token"))
            return
        }

        launchSafe(
            block = {
                uiStateMutable.update { it.copy(isLoading = true) }
                val result = googleTokenAuthUseCase.loginWithGoogle(googleIdToken)
                uiStateMutable.update { it.copy(isLoading = false) }

                result.onSuccess { loginData ->
                    saveLoginData(loginData)

                    if (loginData.isRegistered) {
                        sendUiEffect(NavigateToHome)
                    } else {
                        val userJson = Json.Default.encodeToString(loginData.userPersonalData)
                        sendUiEffect(NavigateToRegistration(userJson))
                    }
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

    private suspend inline fun saveLoginData(loginData: LoginData) {
        showUserDataLog(loginData.userPersonalData)
        saveRefreshTokenUseCase.execute(loginData.refreshToken)
        saveAccessTokenUseCase.execute(loginData.accessToken)
        saveRefreshTokenTimestampUseCase.execute(System.currentTimeMillis())
        savePersonalDataUseCase.execute(loginData.userPersonalData)
        saveIsRegisteredUseCase.execute(loginData.isRegistered)
    }

    private fun showUserDataLog(user: PersonalData) {
        VolleyUiUtil.showDebugLog(
            AuthRepositoryImpl.Companion.TAG,
            "USER section start ========================="
        )
        VolleyUiUtil.showDebugLog(AuthRepositoryImpl.Companion.TAG, "name = ${user.firstName}")
        VolleyUiUtil.showDebugLog(AuthRepositoryImpl.Companion.TAG, "lastName = ${user.lastName}")
        VolleyUiUtil.showDebugLog(AuthRepositoryImpl.Companion.TAG, "avatar = ${user.avatar}")
        VolleyUiUtil.showDebugLog(AuthRepositoryImpl.Companion.TAG, "gender = ${user.gender}")
        VolleyUiUtil.showDebugLog(AuthRepositoryImpl.Companion.TAG, "level = ${user.level}")
        VolleyUiUtil.showDebugLog(
            AuthRepositoryImpl.Companion.TAG,
            "dateOfBirth = ${user.birthDate}"
        )
        VolleyUiUtil.showDebugLog(AuthRepositoryImpl.Companion.TAG, "country = ${user.countryId}")
        VolleyUiUtil.showDebugLog(AuthRepositoryImpl.Companion.TAG, "city = ${user.cityId}")
        VolleyUiUtil.showDebugLog(
            AuthRepositoryImpl.Companion.TAG,
            "USER section end ========================="
        )
    }
}
