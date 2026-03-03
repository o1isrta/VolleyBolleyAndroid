package cy.volleybolley.core.presentation.ui.screens.games.gameinvites

import cy.volleybolley.core.presentation.base.BaseViewModel

class JoinTheTourneyScreenViewModel : BaseViewModel<JoinTheTourneyScreenState, JoinTheTourneyScreenEvent, JoinTheTourneyScreenEffect>(
    initialState = JoinTheTourneyScreenState()
) {
    override val tag: String = "JoinTheTourneyScreenViewModel"

    override fun obtainEvent(event: JoinTheTourneyScreenEvent) {
        when (event) {
            is JoinTheTourneyScreenEvent.OnBackClicked -> {
                sendUiEffect(JoinTheTourneyScreenEffect.NavigateBack)
            }
        }
    }
}
