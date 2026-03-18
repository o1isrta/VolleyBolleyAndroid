package cy.volleybolley.auth.ui.screens.authorization

import cy.volleybolley.auth.data.AuthRepositoryImpl
import cy.volleybolley.auth.domain.api.usecase.GoogleTokenAuthUseCase
import cy.volleybolley.auth.domain.api.usecase.SaveAccessTokenUseCase
import cy.volleybolley.auth.domain.api.usecase.SaveIsRegisteredUseCase
import cy.volleybolley.auth.domain.api.usecase.SavePersonalDataUseCase
import cy.volleybolley.auth.domain.api.usecase.SaveRefreshTokenTimestampUseCase
import cy.volleybolley.auth.domain.api.usecase.SaveRefreshTokenUseCase
import cy.volleybolley.auth.domain.models.LoginData
import cy.volleybolley.auth.ui.screens.authorization.AuthorizationEffect.NavigateToHome
import cy.volleybolley.auth.ui.screens.authorization.AuthorizationEffect.NavigateToRegistration
import cy.volleybolley.auth.ui.screens.authorization.AuthorizationEffect.ShowToast
import cy.volleybolley.auth.ui.screens.authorization.AuthorizationEvent.GoogleTokenReceived
import cy.volleybolley.core.domain.model.VolleyResult
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
    override fun obtainEvent(event: AuthorizationEvent) {
        when (event) {
            is GoogleTokenReceived -> onGoogleTokenReceived(event.googleIdToken)
            AuthorizationEvent.GoogleSignInStarted -> onGoogleSignInStarted()
            AuthorizationEvent.GoogleSignInFailed -> onGoogleSignInFailed()
            AuthorizationEvent.ContinueWithFacebookClicked -> { /* Handle Facebook Auth */ }
        }
    }

    private fun onGoogleSignInStarted() {
        uiStateMutable.update { it.copy(isGoogleLoading = true) }
    }

    private fun onGoogleSignInFailed() {
        uiStateMutable.update { it.copy(isGoogleLoading = false) }
        sendUiEffect(ShowToast(message = "Google Sign-In error"))
    }

    private fun onGoogleTokenReceived(googleIdToken: String) {
        launchSafe(
            block = {
                when (val result = googleTokenAuthUseCase.loginWithGoogle(googleIdToken)) {
                    is VolleyResult.Success -> {
                        saveLoginData(result.data)
                        if (result.data.isRegistered) {
                            sendUiEffect(NavigateToHome)
                        } else {
                            val userJson = Json.encodeToString(result.data.userPersonalData)
                            sendUiEffect(NavigateToRegistration(userJson))
                        }
                    }
                    is VolleyResult.Failure -> sendUiEffect(ShowToast(message = "Authorization error: ${result.error}"))
                }
            },
            onError = {
                uiStateMutable.update { it.copy(isGoogleLoading = false) }
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
            AuthRepositoryImpl.TAG,
            "USER section start ========================="
        )
        VolleyUiUtil.showDebugLog(AuthRepositoryImpl.TAG, "name = ${user.firstName}")
        VolleyUiUtil.showDebugLog(AuthRepositoryImpl.TAG, "lastName = ${user.lastName}")
        VolleyUiUtil.showDebugLog(AuthRepositoryImpl.TAG, "avatar = ${user.avatar}")
        VolleyUiUtil.showDebugLog(AuthRepositoryImpl.TAG, "gender = ${user.gender}")
        VolleyUiUtil.showDebugLog(AuthRepositoryImpl.TAG, "level = ${user.level}")
        VolleyUiUtil.showDebugLog(
            AuthRepositoryImpl.TAG,
            "dateOfBirth = ${user.birthDate}"
        )
        VolleyUiUtil.showDebugLog(AuthRepositoryImpl.TAG, "country = ${user.countryId}")
        VolleyUiUtil.showDebugLog(AuthRepositoryImpl.TAG, "city = ${user.cityId}")
        VolleyUiUtil.showDebugLog(
            AuthRepositoryImpl.TAG,
            "USER section end ========================="
        )
    }
}
