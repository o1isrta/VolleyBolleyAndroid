package cy.volleybolley.core.presentation.ui.screens.home.success

import cy.volleybolley.core.presentation.base.BaseViewModel

class SuccessViewModel(
    createdEvent: SucceedGame,
) :
    BaseViewModel<SuccessState, SuccessEvent, SuccessEffect>(SuccessState(event = createdEvent)) {

    override val tag: String = SuccessViewModel::class.simpleName ?: ""

    override fun obtainEvent(event: SuccessEvent) {
        when (event) {
            SuccessEvent.OnDoneClick -> sendUiEffect(SuccessEffect.CloseScreen)
            is SuccessEvent.OnInvitePlayers -> sendUiEffect(SuccessEffect.NavigateToInvitePlayers)
        }
    }
}
