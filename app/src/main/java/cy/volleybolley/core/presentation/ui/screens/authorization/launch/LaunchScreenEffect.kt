package cy.volleybolley.core.presentation.ui.screens.authorization.launch

import cy.volleybolley.core.presentation.base.UiEffect

sealed interface LaunchScreenEffect : UiEffect {
    data object NavigateToOnboarding : LaunchScreenEffect
    data object NavigateToHome : LaunchScreenEffect
    data object NavigateToAuthorization : LaunchScreenEffect
    class ShowToast(val message: String) : LaunchScreenEffect
}
