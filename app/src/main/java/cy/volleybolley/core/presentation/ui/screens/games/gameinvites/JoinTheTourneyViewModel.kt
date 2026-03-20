package cy.volleybolley.core.presentation.ui.screens.games.gameinvites

import cy.volleybolley.core.presentation.base.BaseViewModel

class JoinTheTourneyViewModel : BaseViewModel<JoinTheTourneyState, JoinTheTourneyEvent, JoinTheTourneyEffect>(
    initialState = JoinTheTourneyState()
) {
    override fun obtainEvent(event: JoinTheTourneyEvent) {
        when (event) {
            is JoinTheTourneyEvent.OnBackClicked -> {
                sendUiEffect(JoinTheTourneyEffect.NavigateBack)
            }
        }
    }
}
