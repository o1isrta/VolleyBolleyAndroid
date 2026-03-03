package cy.volleybolley.core.presentation.ui.screens.games.archive.teamsscreen.event

import cy.volleybolley.core.presentation.base.UiEvent

sealed interface TeamsScreenEvent : UiEvent {
    data object OnBackClicked : TeamsScreenEvent
}
