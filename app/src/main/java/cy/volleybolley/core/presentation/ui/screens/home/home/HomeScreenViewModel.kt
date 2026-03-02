package cy.volleybolley.core.presentation.ui.screens.home.home

import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.model.VolleyMocks
import cy.volleybolley.core.presentation.ui.navigation.SearchCourtRoute
import cy.volleybolley.core.presentation.ui.screens.home.home.HomeScreenEvent.OnCreateNewGameClick
import cy.volleybolley.core.presentation.ui.screens.home.home.HomeScreenEvent.OnCreateTourneyClick
import cy.volleybolley.core.presentation.ui.screens.home.home.HomeScreenEvent.OnDonateClick
import cy.volleybolley.core.presentation.ui.screens.home.home.HomeScreenEvent.OnFindGameClick
import cy.volleybolley.games.domain.model.event.EventType
import kotlinx.coroutines.flow.update

class HomeScreenViewModel : BaseViewModel<HomeScreenState, HomeScreenEvent, HomeScreenEffect>(
    initialState = HomeScreenState()
) {
    init {
        uiStateMutable.update { it.copy(nearGamesCount = 1, location = VolleyMocks.mockLocation) }
    }

    override val tag: String = HomeScreenViewModel::class.simpleName ?: "HomeScreenViewModel"

    override fun obtainEvent(event: HomeScreenEvent) {
        when (event) {
            OnCreateNewGameClick -> openSearchCourt(EventType.GAME)
            OnCreateTourneyClick -> openSearchCourt(EventType.TOURNAMENT)
            OnDonateClick -> {}
            OnFindGameClick -> {}
        }
    }

    private fun openSearchCourt(event: EventType) {
        sendUiEffect(
            HomeScreenEffect.NavigateFromHomeScreen(
                route = SearchCourtRoute(eventType = event)
            )
        )
    }
}
