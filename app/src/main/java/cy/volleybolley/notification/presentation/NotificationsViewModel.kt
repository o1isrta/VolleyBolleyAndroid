package cy.volleybolley.notification.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.google.firebase.FirebaseException
import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.notification.domain.api.notifications.NotificationsUseCase
import cy.volleybolley.notification.presentation.model.NotificationsEffect
import cy.volleybolley.notification.presentation.model.NotificationsEvent
import cy.volleybolley.notification.presentation.model.NotificationsState
import cy.volleybolley.notification.presentation.model.toUi
import cy.volleybolley.notification.presentation.ui.model.NotificationErrorType
import cy.volleybolley.notification.presentation.ui.model.NotificationItem
import cy.volleybolley.notification.utils.Constants.ERROR_MARK_READ
import io.ktor.network.sockets.SocketTimeoutException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.io.IOException
import java.util.concurrent.TimeoutException
import kotlin.coroutines.cancellation.CancellationException

class NotificationsViewModel(
    private val notificationsUseCase: NotificationsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(NotificationsState())
    val state: StateFlow<NotificationsState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<NotificationsEffect>()
    val effect: SharedFlow<NotificationsEffect> = _effect.asSharedFlow()

    fun onEvent(event: NotificationsEvent) {
        when (event) {
            is NotificationsEvent.LoadNotifications -> handleLoadNotifications()
            is NotificationsEvent.OnNotificationClick -> handleNotificationClick(event.notification)
        }
    }

    private fun handleLoadNotifications() = launchHandling {
        _state.update { it.copy(isLoading = true, error = null) }

        val result = notificationsUseCase.getNotifications()
        handleResult(
            result,
            onSuccess = { data ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        notifications = data.map { it.toUi() }
                    )
                }
            },
            onFailure = { error ->
                _state.update { it.copy(isLoading = false, error = error) }
            }
        )
    }

    private fun handleNotificationClick(notification: NotificationItem) = launchHandling {
        val result = notificationsUseCase.markNotificationsAsRead(listOf(notification.id))

        handleResult(
            result,
            onSuccess = {
                notification.screen?.let { screen ->
                    _effect.emit(NotificationsEffect.NavigateTo(screen))
                }
            },
            onFailure = {
                _effect.emit(NotificationsEffect.ShowError(ERROR_MARK_READ))
            }
        )
    }

    private suspend fun <T> handleResult(
        result: VolleyResult<T, ErrorType>,
        onSuccess: suspend (T) -> Unit,
        onFailure: suspend (ErrorType) -> Unit
    ) {
        when (result) {
            is VolleyResult.Success -> onSuccess(result.data)
            is VolleyResult.Failure -> onFailure(result.error)
        }
    }

    private fun launchHandling(block: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    block()
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: SocketTimeoutException) {
                _effect.emit(NotificationsEffect.ShowError(handleError(e).message))
            } catch (e: TimeoutException) {
                _effect.emit(NotificationsEffect.ShowError(handleError(e).message))
            } catch (e: IOException) {
                _effect.emit(NotificationsEffect.ShowError(handleError(e).message))
            } catch (e: HttpException) {
                _effect.emit(NotificationsEffect.ShowError(handleError(e).message))
            } catch (e: FirebaseException) {
                _effect.emit(NotificationsEffect.ShowError(handleError(e).message))
            }
        }
    }

    private fun handleError(e: Exception): NotificationErrorType {
        return when (e) {
            is SocketTimeoutException,
            is TimeoutException -> {
                Log.e(TAG, "${NotificationErrorType.TIMEOUT.name}: ${e.message}", e)
                NotificationErrorType.TIMEOUT
            }

            is IOException -> {
                Log.e(TAG, "${NotificationErrorType.NETWORK.name}: ${e.message}", e)
                NotificationErrorType.NETWORK
            }

            is HttpException -> {
                Log.e(TAG, "${NotificationErrorType.SERVER.name}: ${e.message}", e)
                NotificationErrorType.SERVER
            }

            is FirebaseException -> {
                Log.e(TAG, "${NotificationErrorType.FIREBASE.name}: ${e.message}", e)
                NotificationErrorType.FIREBASE
            }

            else -> {
                Log.e(TAG, "${NotificationErrorType.UNKNOWN.name}: ${e.message}", e)
                NotificationErrorType.UNKNOWN
            }
        }
    }

    companion object {
        private const val TAG = "NotificationsViewModel"
    }
}
