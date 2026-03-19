package cy.volleybolley.core.presentation.ui.screens.games.upcominggames

import cy.volleybolley.core.presentation.base.BaseViewModel

class UpcomingGameDetailsScreenViewModel : BaseViewModel<UpcomingGameDetailsScreenState, UpcomingGameDetailsScreenEvent, UpcomingGameDetailsScreenEffect>(
    initialState = UpcomingGameDetailsScreenState()
) {
    override fun obtainEvent(event: UpcomingGameDetailsScreenEvent) {
        when (event) {
            is UpcomingGameDetailsScreenEvent.OnBackClicked -> {
                sendUiEffect(UpcomingGameDetailsScreenEffect.NavigateBack)
            }
        }
    }
}
