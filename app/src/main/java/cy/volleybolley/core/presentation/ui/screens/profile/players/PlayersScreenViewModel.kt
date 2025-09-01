package cy.volleybolley.core.presentation.ui.screens.profile.players

import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.screens.profile.players.model.BackPlayerHolder
import kotlinx.serialization.json.Json

class PlayersScreenViewModel(
    private val backPlayerHolder: BackPlayerHolder,
    private val json: Json,
) : BaseViewModel<PlayersScreenState, PlayersScreenEvent, PlayersScreenEffect> (
    initialState = PlayersScreenState()
) {
    override val tag = TAG

    override fun obtainEvent(event: PlayersScreenEvent) {

    }

    companion object {
        val TAG = PlayersScreenViewModel::class.simpleName ?: "PlayersScreenViewModel"
    }
}
