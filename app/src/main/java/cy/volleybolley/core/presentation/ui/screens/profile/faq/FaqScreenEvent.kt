package cy.volleybolley.core.presentation.ui.screens.profile.faq

import cy.volleybolley.core.presentation.base.UiEvent

sealed interface FaqScreenEvent : UiEvent {
    data object OnBackFromFaqClick: FaqScreenEvent
}
