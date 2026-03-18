package cy.volleybolley.core.presentation

import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.messaging.FirebaseMessaging
import cy.volleybolley.auth.domain.api.usecase.CheckRefreshTokenExpirationUseCase
import cy.volleybolley.auth.domain.api.usecase.ClearAllLoginDataUseCase
import cy.volleybolley.auth.domain.api.usecase.GetAuthenticatedStatusUseCase
import cy.volleybolley.auth.domain.api.usecase.GetPersonalDataUseCase
import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.model.state.data.DialogData
import cy.volleybolley.notification.domain.api.permission.NotificationPermissionChecker
import cy.volleybolley.notification.domain.api.registration.SendDeviceTokenUseCase
import cy.volleybolley.notification.domain.api.storages.FCMTokenStore
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.tasks.await
import kotlinx.io.IOException
import kotlin.coroutines.cancellation.CancellationException

class MainActivityViewModel(
    private val sendDeviceTokenUseCase: SendDeviceTokenUseCase,
    private val fcmTokenStore: FCMTokenStore,
    private val notificationPermissionChecker: NotificationPermissionChecker,
    private val getAuthenticatedStatusUseCase: GetAuthenticatedStatusUseCase,
    private val getPersonalDataUseCase: GetPersonalDataUseCase,
    private val checkRefreshTokenExpirationUseCase: CheckRefreshTokenExpirationUseCase,
    private val clearAllLoginDataUseCase: ClearAllLoginDataUseCase
) : BaseViewModel<MainActivityState, MainActivityEvent, MainActivityEffect>(
    initialState = MainActivityState()
) {
    init {
        loadAuthState()
    }

    private fun loadAuthState() {
        launchSafe(
            getErrorLogMessage = { "Failed to load auth state: $it" }
        ) {
            val isAuthenticated = getAuthenticatedStatusUseCase.execute()
            val personalData = if (isAuthenticated) {
                getPersonalDataUseCase.execute()
            } else {
                null
            }
            uiStateMutable.update {
                it.copy(
                    isAuthenticated = isAuthenticated,
                    personalData = personalData,
                    isReady = true
                )
            }
            if (isAuthenticated) {
                updateTokenBasedOnPermission()
            }
        }
    }

    fun isNotificationPermissionGranted(): Boolean {
        return notificationPermissionChecker.isNotificationPermissionGranted()
    }

    fun updateTokenBasedOnPermission() {
        launchSafe(
            getErrorLogMessage = { "Failed to check notification permission or update token: $it" }
        ) {
            val hasPermission = notificationPermissionChecker.isNotificationPermissionGranted()
            if (hasPermission) {
                updateFcmTokenIfNeeded()
            }
            updateNotificationPermissionState(hasPermission)
        }
    }

    private suspend fun updateFcmTokenIfNeeded() {
        val oldToken = fcmTokenStore.getToken()
        val newToken = fetchFirebaseToken()
        if (newToken != null && newToken != oldToken) {
            fcmTokenStore.saveToken(newToken)
            sendDeviceTokenUseCase.updateToken(newToken)
            Log.d("FCM", "Token updated: old=$oldToken new=$newToken")
        }
    }

    private suspend fun fetchFirebaseToken(): String? {
        return try {
            FirebaseMessaging.getInstance().token.await()
        } catch (e: IOException) {
            Log.e(tag, IO_ERROR_MSG, e)
            null
        } catch (e: FirebaseException) {
            Log.e(tag, FIREBASE_ERROR_MSG, e)
            null
        } catch (e: CancellationException) {
            throw e
        }
    }

    private fun updateNotificationPermissionState(hasPermission: Boolean) {
        uiStateMutable.update {
            it.copy(notificationPermissionGranted = hasPermission)
        }
    }

    override fun obtainEvent(event: MainActivityEvent) {
        when (event) {
            is MainActivityEvent.IntentReceived -> {
                uiStateMutable.update {
                    it.copy(screen = event.screen, eventId = event.eventId)
                }
            }

            is MainActivityEvent.NotificationPermissionChanged -> {
                uiStateMutable.update { it.copy(notificationPermissionGranted = event.granted) }
            }

            is MainActivityEvent.DismissGlobalDialog -> {
                dismissGlobalDialog()
            }

            is MainActivityEvent.RequestPermission -> {
                sendUiEffect(MainActivityEffect.RequestNotificationPermission)
            }

            is MainActivityEvent.OnStop -> {
                checkTokenExpiration()
            }
        }
    }

    private fun checkTokenExpiration() {
        launchSafe(
            getErrorLogMessage = { "Failed to check token expiration: $it" }
        ) {
            val shouldLogout = checkRefreshTokenExpirationUseCase.execute()
            if (shouldLogout) {
                clearAllLoginDataUseCase.execute()
                loadAuthState()
            }
        }
    }

    fun showNotificationPermissionDialog(title: String, message: String, onConfirm: () -> Unit) {
        uiStateMutable.update {
            it.copy(
                globalDialog = DialogData(
                    title = title,
                    message = message,
                    onConfirm = onConfirm,
                    onDismiss = { obtainEvent(MainActivityEvent.DismissGlobalDialog) }
                )
            )
        }
    }

    private fun dismissGlobalDialog() {
        uiStateMutable.update { it.copy(globalDialog = null) }
    }

    private companion object {
        const val IO_ERROR_MSG = "Token fetch failed: IO error"
        const val FIREBASE_ERROR_MSG = "Token fetch failed: Firebase error"
    }
}
