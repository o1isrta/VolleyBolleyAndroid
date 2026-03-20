package cy.volleybolley.core.presentation.ui.screens.findatourney

import cy.volleybolley.core.presentation.base.BaseViewModel

class IndividualPlayersViewModel :
    BaseViewModel<IndividualPlayersState, IndividualPlayersEvent, IndividualPlayersEffect>(
        initialState = IndividualPlayersState()
    ) {
    override fun obtainEvent(event: IndividualPlayersEvent) {
        when (event) {
            is IndividualPlayersEvent.OnBackClicked -> {
                sendUiEffect(IndividualPlayersEffect.NavigateBack)
            }
        }
    }
}
