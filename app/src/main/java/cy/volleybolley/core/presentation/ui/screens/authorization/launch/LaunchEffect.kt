package cy.volleybolley.core.presentation.ui.screens.authorization.launch

import cy.volleybolley.core.presentation.base.UiEffect

sealed class LaunchEffect : UiEffect {
    object NavigateToOnboarding : LaunchEffect()
}
