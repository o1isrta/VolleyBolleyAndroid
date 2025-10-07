package cy.volleybolley.core.presentation.ui.screens.home.home

import cy.volleybolley.core.presentation.base.UiEvent

sealed interface HomeScreenEvent : UiEvent {
    data object OnCreateNewGameClick : HomeScreenEvent
    data object OnFindGameClick : HomeScreenEvent
    data object OnCreateTourneyClick : HomeScreenEvent
    data object OnDonateClick : HomeScreenEvent
}
