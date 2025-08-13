package cy.volleybolley.core.presentation.ui.screens.profile.event

import cy.volleybolley.core.presentation.base.UiEvent

sealed interface PersonalDataScreenEvent : UiEvent {
    data object OnPlayersClick : PersonalDataScreenEvent
}
