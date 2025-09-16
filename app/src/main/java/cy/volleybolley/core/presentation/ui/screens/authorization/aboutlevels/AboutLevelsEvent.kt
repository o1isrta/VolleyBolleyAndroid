package cy.volleybolley.core.presentation.ui.screens.authorization.aboutlevels

import cy.volleybolley.core.presentation.base.UiEvent

sealed class AboutLevelsEvent : UiEvent {
    object OnBackClicked : AboutLevelsEvent()
}

