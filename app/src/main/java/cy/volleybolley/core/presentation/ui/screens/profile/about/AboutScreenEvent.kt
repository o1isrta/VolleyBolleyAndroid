package cy.volleybolley.core.presentation.ui.screens.profile.about

import cy.volleybolley.core.presentation.base.UiEvent

sealed interface AboutScreenEvent : UiEvent {
    data object OnBackFromAboutClick: AboutScreenEvent
}
