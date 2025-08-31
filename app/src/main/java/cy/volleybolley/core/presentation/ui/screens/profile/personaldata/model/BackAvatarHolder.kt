package cy.volleybolley.core.presentation.ui.screens.profile.personaldata.model

import androidx.lifecycle.SavedStateHandle

class BackAvatarHolder(private val savedStateHandle: SavedStateHandle) {
    fun getAvatarChanges(): String? = savedStateHandle.get<String>(AVATAR_KEY)

    fun clearBackAvatar() {
        savedStateHandle.remove<String>(AVATAR_KEY)
    }

    companion object {
        const val AVATAR_KEY = "avatar_key"
    }
}
