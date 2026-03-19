package cy.volleybolley.core.presentation.ui.screens.home.home

import cy.volleybolley.core.presentation.base.UiEffect
import cy.volleybolley.games.domain.model.event.EventType

interface HomeScreenEffect : UiEffect {
    data class NavigateToSearchCourt(val eventType: EventType) : HomeScreenEffect
    data object RequestNotificationPermission : HomeScreenEffect
}
