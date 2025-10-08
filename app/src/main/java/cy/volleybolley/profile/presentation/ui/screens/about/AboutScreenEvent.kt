package cy.volleybolley.profile.presentation.ui.screens.about

import cy.volleybolley.core.presentation.base.UiEvent

sealed interface AboutScreenEvent : UiEvent {
    data object OnBackFromAboutClick : AboutScreenEvent
}
