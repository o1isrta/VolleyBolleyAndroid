package cy.volleybolley.core.presentation.ui.screens.games.upcominggames

import cy.volleybolley.core.presentation.base.BaseViewModel

class UpcomingGamesViewModel : BaseViewModel<UpcomingGamesState, UpcomingGamesEvent, UpcomingGamesEffect>(
    initialState = UpcomingGamesState()
) {
    override fun obtainEvent(event: UpcomingGamesEvent) {
        when (event) {
            is UpcomingGamesEvent.OnBackClicked -> {
                sendUiEffect(UpcomingGamesEffect.NavigateBack)
            }
        }
    }
}
