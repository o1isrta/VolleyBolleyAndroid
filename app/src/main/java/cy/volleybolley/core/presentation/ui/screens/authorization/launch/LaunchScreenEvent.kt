package cy.volleybolley.core.presentation.ui.screens.authorization.launch

import cy.volleybolley.core.presentation.base.UiEvent

sealed interface LaunchScreenEvent : UiEvent {
    data object RetryClicked : LaunchScreenEvent
}
