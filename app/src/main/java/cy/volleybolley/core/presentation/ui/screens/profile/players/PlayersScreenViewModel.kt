package cy.volleybolley.core.presentation.ui.screens.profile.players

import cy.volleybolley.core.presentation.base.BaseViewModel

class PlayersScreenViewModel : BaseViewModel<PlayersScreenState, PlayersScreenEvent, PlayersScreenEffect> (
    initialState = PlayersScreenState()
) {
    override val tag = TAG

    override fun obtainEvent(event: PlayersScreenEvent) {

    }

    companion object {
        val TAG = PlayersScreenViewModel::class.simpleName ?: "PlayersScreenViewModel"
    }
}
