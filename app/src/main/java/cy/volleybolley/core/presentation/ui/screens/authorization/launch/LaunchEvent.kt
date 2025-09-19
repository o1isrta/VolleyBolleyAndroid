package cy.volleybolley.core.presentation.ui.screens.authorization.launch

import cy.volleybolley.core.presentation.base.UiEvent

sealed class LaunchEvent : UiEvent {
    object Start : LaunchEvent()
}
