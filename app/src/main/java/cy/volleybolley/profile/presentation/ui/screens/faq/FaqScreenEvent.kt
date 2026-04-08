package cy.volleybolley.profile.presentation.ui.screens.faq

import cy.volleybolley.core.presentation.base.UiEvent

sealed interface FaqScreenEvent : UiEvent {
    data object OnBackFromFaqClick : FaqScreenEvent
    data object RetryClick : FaqScreenEvent
}
