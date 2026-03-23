package cy.volleybolley.core.presentation.ui.screens.games.gameinvites

import cy.volleybolley.core.presentation.base.BaseViewModel

class GameInvitesViewModel : BaseViewModel<GameInvitesState, GameInvitesEvent, GameInvitesEffect>(
    initialState = GameInvitesState()
) {
    override fun obtainEvent(event: GameInvitesEvent) {
        when (event) {
            is GameInvitesEvent.OnBackClicked -> {
                sendUiEffect(GameInvitesEffect.NavigateBack)
            }
        }
    }
}
