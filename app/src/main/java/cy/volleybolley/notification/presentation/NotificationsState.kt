package cy.volleybolley.notification.presentation

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.presentation.base.UiState
import cy.volleybolley.notification.presentation.ui.model.NotificationItem

data class NotificationsState(
    val isLoading: Boolean = false,
    val notifications: List<NotificationItem> = emptyList(),
    val error: ErrorType? = null
) : UiState
