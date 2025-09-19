package cy.volleybolley.core.presentation.ui.screens.authorization.onboarding

import cy.volleybolley.core.presentation.base.UiEffect

sealed class OnboardingEffect : UiEffect {
    object NavigateToSignUp : OnboardingEffect()
}
