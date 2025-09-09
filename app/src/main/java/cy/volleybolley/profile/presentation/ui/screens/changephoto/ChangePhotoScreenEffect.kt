package cy.volleybolley.profile.presentation.ui.screens.changephoto

import cy.volleybolley.core.presentation.base.UiEffect

sealed interface ChangePhotoScreenEffect : UiEffect {
    data class NavigateFromChangePhotoScreen(val avatarArgument: String?) : ChangePhotoScreenEffect
}
