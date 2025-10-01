package cy.volleybolley.notification.presentation

import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.notification.domain.api.notifications.NotificationsUseCase
import cy.volleybolley.notification.presentation.model.toUi
import cy.volleybolley.notification.presentation.ui.model.NotificationItem
import kotlinx.coroutines.flow.update

class NotificationsViewModel(
    private val notificationsUseCase: NotificationsUseCase
) : BaseViewModel<NotificationsState, NotificationsEvent, NotificationsEffect>(
    initialState = NotificationsState()
) {

    override val tag: String = NotificationsViewModel::class.simpleName ?: "NotificationsVM"

    override fun obtainEvent(event: NotificationsEvent) {
        when (event) {
            is NotificationsEvent.LoadNotifications -> loadNotifications()
            is NotificationsEvent.OnNotificationClick -> onNotificationClick(event.notification)
        }
    }

    private fun loadNotifications() {
        launchSafe(
            getErrorLogMessage = { "${ERROR_LOADING_NOTIFICATIONS}: ${it.message}" },
        ) {
            uiStateMutable.update { it.copy(isLoading = true, error = null) }

            when (val result = notificationsUseCase.getNotifications()) {
                is VolleyResult.Success -> {
                    uiStateMutable.update {
                        it.copy(
                            isLoading = false,
                            notifications = result.data.map { it.toUi() }
                        )
                    }
                }

                is VolleyResult.Failure -> {
                    uiStateMutable.update {
                        it.copy(isLoading = false, error = result.error)
                    }
                }
            }
        }
    }

    private fun onNotificationClick(notification: NotificationItem) {
        launchSafe(
            getErrorLogMessage = { "${ERROR_MARK_READ}: ${it.message}" },
            onError = { e ->
                sendUiEffect(NotificationsEffect.ShowError(ERROR_MARK_READ))
            }
        ) {
            when (notificationsUseCase.markNotificationsAsRead(listOf(notification.notificationId))) {
                is VolleyResult.Success -> {
                    notification.screen?.let { screen ->
                        sendUiEffect(NotificationsEffect.NavigateTo(screen))
                    }
                }

                is VolleyResult.Failure -> {
                    sendUiEffect(NotificationsEffect.ShowError(ERROR_MARK_READ))
                }
            }
        }
    }

    companion object {
        const val ERROR_MARK_READ = "Error marking notification as read"
        const val ERROR_LOADING_NOTIFICATIONS = "Error loading notifications"
    }
}
