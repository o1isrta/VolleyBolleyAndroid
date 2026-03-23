package cy.volleybolley.core.presentation.ui.screens.games.upcominggames

import cy.volleybolley.core.presentation.base.BaseViewModel

class UpcomingGameDetailsViewModel :
    BaseViewModel<UpcomingGameDetailsState, UpcomingGameDetailsEvent, UpcomingGameDetailsEffect>(
        initialState = UpcomingGameDetailsState()
    ) {
    override fun obtainEvent(event: UpcomingGameDetailsEvent) {
        when (event) {
            is UpcomingGameDetailsEvent.OnBackClicked -> {
                sendUiEffect(UpcomingGameDetailsEffect.NavigateBack)
            }
        }
    }
}
