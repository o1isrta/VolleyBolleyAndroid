package cy.volleybolley.core.presentation.ui.screens.games.upcominggames

import cy.volleybolley.core.presentation.base.UiEvent

sealed interface UpcomingTourneyDetailsScreenEvent : UiEvent {
    data object OnBackClicked : UpcomingTourneyDetailsScreenEvent
    data object OnViewPlayersClicked : UpcomingTourneyDetailsScreenEvent
}
