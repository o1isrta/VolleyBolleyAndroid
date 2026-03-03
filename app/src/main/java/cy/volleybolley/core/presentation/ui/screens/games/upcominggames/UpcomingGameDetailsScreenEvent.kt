package cy.volleybolley.core.presentation.ui.screens.games.upcominggames

import cy.volleybolley.core.presentation.base.UiEvent

sealed interface UpcomingGameDetailsScreenEvent : UiEvent {
    data object OnBackClicked : UpcomingGameDetailsScreenEvent
}
