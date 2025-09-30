package cy.volleybolley.core.presentation.ui.screens.home.home

import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.screens.home.home.HomeScreenEvent.OnCreateNewGameClick
import cy.volleybolley.core.presentation.ui.screens.home.home.HomeScreenEvent.OnCreateTourneyClick
import cy.volleybolley.core.presentation.ui.screens.home.home.HomeScreenEvent.OnDonateClick
import cy.volleybolley.core.presentation.ui.screens.home.home.HomeScreenEvent.OnFindGameClick

class HomeScreenViewModel(): BaseViewModel<HomeScreenState, HomeScreenEvent, HomeScreenEffect>(
    initialState = HomeScreenState()
) {
    override val tag: String = HomeScreenViewModel::class.simpleName ?: "HomeScreenViewModel"

    override fun obtainEvent(event: HomeScreenEvent) {
        when(event) {
            OnCreateNewGameClick -> {}
            OnCreateTourneyClick -> {}
            OnDonateClick -> {}
            OnFindGameClick -> {}
        }
    }
}
