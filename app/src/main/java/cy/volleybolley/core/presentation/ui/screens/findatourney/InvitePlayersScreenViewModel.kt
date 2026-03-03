package cy.volleybolley.core.presentation.ui.screens.findatourney

import cy.volleybolley.core.presentation.base.BaseViewModel

class InvitePlayersScreenViewModel : BaseViewModel<InvitePlayersScreenState, InvitePlayersScreenEvent, InvitePlayersScreenEffect>(
    initialState = InvitePlayersScreenState()
) {
    override val tag: String = "InvitePlayersScreenViewModel"

    override fun obtainEvent(event: InvitePlayersScreenEvent) {
        when (event) {
            is InvitePlayersScreenEvent.OnBackClicked -> {
                sendUiEffect(InvitePlayersScreenEffect.NavigateBack)
            }
        }
    }
}
