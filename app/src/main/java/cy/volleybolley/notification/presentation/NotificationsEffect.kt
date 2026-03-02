package cy.volleybolley.notification.presentation

import cy.volleybolley.core.presentation.base.UiEffect

sealed class NotificationsEffect : UiEffect {
    data class ShowError(val message: String) : NotificationsEffect()
    data class NavigateTo(val screen: String) : NotificationsEffect()
}
