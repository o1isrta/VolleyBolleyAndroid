package cy.volleybolley.core.presentation.ui.screens.findatourney

import cy.volleybolley.core.presentation.base.BaseViewModel

class JoinTeamScreenViewModel : BaseViewModel<JoinTeamScreenState, JoinTeamScreenEvent, JoinTeamScreenEffect>(
    initialState = JoinTeamScreenState()
) {
    override val tag: String = "JoinTeamScreenViewModel"

    override fun obtainEvent(event: JoinTeamScreenEvent) {
        when (event) {
            is JoinTeamScreenEvent.OnBackClicked -> {
                sendUiEffect(JoinTeamScreenEffect.NavigateBack)
            }
        }
    }
}
