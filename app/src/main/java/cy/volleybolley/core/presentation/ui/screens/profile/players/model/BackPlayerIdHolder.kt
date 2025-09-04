package cy.volleybolley.core.presentation.ui.screens.profile.players.model

import androidx.lifecycle.SavedStateHandle

class BackPlayerIdHolder(private val savedStateHandle: SavedStateHandle) {
    fun getPlayerId(): Int? = savedStateHandle.get<Int>(PLAYER_ID_KEY)

    fun clearBackPlayerId() {
        savedStateHandle.remove<Int>(PLAYER_ID_KEY)
    }

    companion object {
        const val PLAYER_ID_KEY = "player_id_key"
    }
}
