package cy.volleybolley.core.presentation.ui.screens.profile.changephoto

import cy.volleybolley.core.presentation.base.UiEffect

sealed interface ChangePhotoScreenEffect : UiEffect{
    data class NavigateFromChangePhotoScreen(val avatarArgument: String?) : ChangePhotoScreenEffect
}
