package cy.volleybolley.core.presentation.ui.screens.profile.players.model

import androidx.lifecycle.SavedStateHandle

class BackPlayerHolder(private val savedStateHandle: SavedStateHandle) {
    fun getPlayerJsonString(): String? = savedStateHandle.get<String>(PLAYER_KEY)

    fun clearBackPlayer() {
        savedStateHandle.remove<String>(PLAYER_KEY)
    }

    companion object {
        const val PLAYER_KEY = "player_key"
    }
}
