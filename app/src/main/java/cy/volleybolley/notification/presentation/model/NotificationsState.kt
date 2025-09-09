package cy.volleybolley.notification.presentation.model

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.notification.ui.model.NotificationItem

data class NotificationsState(
    val isLoading: Boolean = false,
    val notifications: List<NotificationItem> = emptyList(),
    val error: ErrorType? = null
)
