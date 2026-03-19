package cy.volleybolley.core.presentation.ui.screens.findatourney

import cy.volleybolley.core.presentation.base.BaseViewModel

class IndividualPlayersScreenViewModel : BaseViewModel<IndividualPlayersScreenState, IndividualPlayersScreenEvent, IndividualPlayersScreenEffect>(
    initialState = IndividualPlayersScreenState()
) {
    override fun obtainEvent(event: IndividualPlayersScreenEvent) {
        when (event) {
            is IndividualPlayersScreenEvent.OnBackClicked -> {
                sendUiEffect(IndividualPlayersScreenEffect.NavigateBack)
            }
        }
    }
}
