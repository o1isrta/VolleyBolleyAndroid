package cy.volleybolley.core.presentation.ui.screens.games.upcominggames

import cy.volleybolley.core.presentation.base.UiEvent

sealed interface UpcomingTourneyDetailsEvent : UiEvent {
    data object OnBackClicked : UpcomingTourneyDetailsEvent
    data object OnViewPlayersClicked : UpcomingTourneyDetailsEvent
}
