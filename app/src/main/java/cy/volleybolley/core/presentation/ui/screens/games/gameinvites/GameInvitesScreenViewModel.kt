package cy.volleybolley.core.presentation.ui.screens.games.gameinvites

import cy.volleybolley.core.presentation.base.BaseViewModel

class GameInvitesScreenViewModel : BaseViewModel<GameInvitesScreenState, GameInvitesScreenEvent, GameInvitesScreenEffect>(
    initialState = GameInvitesScreenState()
) {
    override fun obtainEvent(event: GameInvitesScreenEvent) {
        when (event) {
            is GameInvitesScreenEvent.OnBackClicked -> {
                sendUiEffect(GameInvitesScreenEffect.NavigateBack)
            }
        }
    }
}
