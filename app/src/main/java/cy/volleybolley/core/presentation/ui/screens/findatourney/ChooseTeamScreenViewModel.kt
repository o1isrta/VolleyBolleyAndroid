package cy.volleybolley.core.presentation.ui.screens.findatourney

import cy.volleybolley.core.presentation.base.BaseViewModel

class ChooseTeamScreenViewModel : BaseViewModel<ChooseTeamScreenState, ChooseTeamScreenEvent, ChooseTeamScreenEffect>(
    initialState = ChooseTeamScreenState()
) {
    override val tag: String = "ChooseTeamScreenViewModel"

    override fun obtainEvent(event: ChooseTeamScreenEvent) {
        when (event) {
            is ChooseTeamScreenEvent.OnBackClicked -> {
                sendUiEffect(ChooseTeamScreenEffect.NavigateBack)
            }
        }
    }
}
