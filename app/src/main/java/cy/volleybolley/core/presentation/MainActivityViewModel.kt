package cy.volleybolley.core.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseException
import com.google.firebase.messaging.FirebaseMessaging
import cy.volleybolley.core.presentation.ui.model.state.MainActivityEvent
import cy.volleybolley.core.presentation.ui.model.state.MainActivityState
import cy.volleybolley.core.presentation.ui.model.state.data.DialogData
import cy.volleybolley.notification.domain.api.FCMTokenStore
import cy.volleybolley.notification.domain.api.NotificationPermissionChecker
import cy.volleybolley.notification.domain.api.registration.SendDeviceTokenUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.io.IOException
import kotlin.coroutines.cancellation.CancellationException

class MainActivityViewModel(
    private val sendDeviceTokenUseCase: SendDeviceTokenUseCase,
    private val fcmTokenStore: FCMTokenStore,
    private val notificationPermissionChecker: NotificationPermissionChecker,
    private val isUserAuthorized: Boolean = false,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainActivityState())
    val uiState: StateFlow<MainActivityState> = _uiState.asStateFlow()

    fun updateTokenBasedOnPermission() {
        viewModelScope.launch {
            if (!isUserAuthorized) {
                _uiState.update { it.copy(isReady = true) }
            } else {
                val hasPermission = notificationPermissionChecker.isNotificationPermissionGranted()

                if (hasPermission) {
                    val oldToken = fcmTokenStore.getToken()
                    val newToken = try {
                        FirebaseMessaging.getInstance().token.await()
                    } catch (e: IOException) {
                        Log.e("FCM", "Token fetch failed: IO error", e)
                        null
                    } catch (e: FirebaseException) {
                        Log.e("FCM", "Token fetch failed: Firebase error", e)
                        null
                    } catch (e: CancellationException) {
                        throw e
                    }
                    if (newToken != null && newToken != oldToken) {
                        fcmTokenStore.saveToken(newToken)
                        sendDeviceTokenUseCase.updateToken(newToken)
                        Log.d("FCM", "Token updated: old=$oldToken new=$newToken")
                    }
                }

                _uiState.update {
                    it.copy(
                        notificationPermissionGranted = hasPermission,
                        isReady = true
                    )
                }
            }
        }
    }


    fun isNotificationPermissionGranted(): Boolean {
        return notificationPermissionChecker.isNotificationPermissionGranted()
    }

    fun onEvent(event: MainActivityEvent) {
        when (event) {
            is MainActivityEvent.IntentReceived -> {
                _uiState.update {
                    it.copy(screen = event.screen, gameId = event.gameId)
                }
            }

            is MainActivityEvent.TokenFetchFailed -> {
                _uiState.update { it.copy(isReady = true) }
            }

            is MainActivityEvent.NotificationPermissionChanged -> {
                _uiState.update { it.copy(notificationPermissionGranted = event.granted) }
            }
        }
    }

    fun showNotificationPermissionDialog(title: String, message: String, onConfirm: () -> Unit) {
        _uiState.update {
            it.copy(
                globalDialog = DialogData(
                    title = title,
                    message = message,
                    onConfirm = onConfirm,
                    onDismiss = { dismissGlobalDialog() }
                )
            )
        }
    }

    fun dismissGlobalDialog() {
        _uiState.update { it.copy(globalDialog = null) }
    }
}
