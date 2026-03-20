package cy.volleybolley.core.presentation.ui.screens.findatourney

import cy.volleybolley.core.presentation.base.BaseViewModel

class InvitePlayersViewModel : BaseViewModel<InvitePlayersState, InvitePlayersEvent, InvitePlayersEffect>(
    initialState = InvitePlayersState()
) {
    override fun obtainEvent(event: InvitePlayersEvent) {
        when (event) {
            is InvitePlayersEvent.OnBackClicked -> {
                sendUiEffect(InvitePlayersEffect.NavigateBack)
            }
        }
    }
}
