package cy.volleybolley.core.presentation.ui.screens.findatourney

import cy.volleybolley.core.presentation.base.BaseViewModel

class JoinTeamScreenViewModel : BaseViewModel<JoinTeamScreenState, JoinTeamScreenEvent, JoinTeamScreenEffect>(
    initialState = JoinTeamScreenState()
) {
    override fun obtainEvent(event: JoinTeamScreenEvent) {
        when (event) {
            is JoinTeamScreenEvent.OnBackClicked -> {
                sendUiEffect(JoinTeamScreenEffect.NavigateBack)
            }
        }
    }
}
