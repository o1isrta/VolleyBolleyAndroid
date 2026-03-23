package cy.volleybolley.core.presentation.ui.screens.findatourney

import cy.volleybolley.core.presentation.base.BaseViewModel

class JoinIndividualViewModel : BaseViewModel<JoinIndividualState, JoinIndividualEvent, JoinIndividualEffect>(
    initialState = JoinIndividualState()
) {
    override fun obtainEvent(event: JoinIndividualEvent) {
        when (event) {
            is JoinIndividualEvent.OnBackClicked -> {
                sendUiEffect(JoinIndividualEffect.NavigateBack)
            }
        }
    }
}
