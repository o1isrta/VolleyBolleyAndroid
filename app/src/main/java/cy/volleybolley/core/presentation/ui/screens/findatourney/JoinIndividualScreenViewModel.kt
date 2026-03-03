package cy.volleybolley.core.presentation.ui.screens.findatourney

import cy.volleybolley.core.presentation.base.BaseViewModel

class JoinIndividualScreenViewModel : BaseViewModel<JoinIndividualScreenState, JoinIndividualScreenEvent, JoinIndividualScreenEffect>(
    initialState = JoinIndividualScreenState()
) {
    override val tag: String = "JoinIndividualScreenViewModel"

    override fun obtainEvent(event: JoinIndividualScreenEvent) {
        when (event) {
            is JoinIndividualScreenEvent.OnBackClicked -> {
                sendUiEffect(JoinIndividualScreenEffect.NavigateBack)
            }
        }
    }
}
