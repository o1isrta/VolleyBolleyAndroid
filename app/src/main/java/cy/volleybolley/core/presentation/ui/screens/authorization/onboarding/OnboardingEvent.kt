package cy.volleybolley.core.presentation.ui.screens.authorization.onboarding

import cy.volleybolley.core.presentation.base.UiEvent

sealed class OnboardingEvent : UiEvent {
    object GetStartedClicked : OnboardingEvent()
}
