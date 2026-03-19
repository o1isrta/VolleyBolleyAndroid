package cy.volleybolley.core.presentation.ui.screens.games.upcominggames

import cy.volleybolley.core.presentation.base.BaseViewModel

class UpcomingGamesScreenViewModel : BaseViewModel<UpcomingGamesScreenState, UpcomingGamesScreenEvent, UpcomingGamesScreenEffect>(
    initialState = UpcomingGamesScreenState()
) {
    override fun obtainEvent(event: UpcomingGamesScreenEvent) {
        when (event) {
            is UpcomingGamesScreenEvent.OnBackClicked -> {
                sendUiEffect(UpcomingGamesScreenEffect.NavigateBack)
            }
        }
    }
}
