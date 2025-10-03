package cy.volleybolley.core.presentation.ui.screens.authorization.launch

import cy.volleybolley.core.presentation.base.UiEffect

sealed interface LaunchEffect : UiEffect {
    object NavigateToOnboarding : LaunchEffect
}
