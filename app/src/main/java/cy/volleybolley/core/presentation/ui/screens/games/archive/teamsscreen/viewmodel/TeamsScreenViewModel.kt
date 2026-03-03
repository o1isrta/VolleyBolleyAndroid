package cy.volleybolley.core.presentation.ui.screens.games.archive.teamsscreen.viewmodel

import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.screens.games.archive.teamsscreen.effect.TeamsScreenEffect
import cy.volleybolley.core.presentation.ui.screens.games.archive.teamsscreen.event.TeamsScreenEvent
import cy.volleybolley.core.presentation.ui.screens.games.archive.teamsscreen.model.TeamsScreenState

class TeamsScreenViewModel : BaseViewModel<TeamsScreenState, TeamsScreenEvent, TeamsScreenEffect>(
    initialState = TeamsScreenState()
) {
    override val tag: String = "TeamsScreenViewModel"

    override fun obtainEvent(event: TeamsScreenEvent) {
        when (event) {
            is TeamsScreenEvent.OnBackClicked -> {
                sendUiEffect(TeamsScreenEffect.NavigateBack)
            }
        }
    }
}
