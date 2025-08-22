package cy.volleybolley.core.presentation.ui.screens.profile.changephoto

import cy.volleybolley.core.presentation.base.UiEvent

sealed interface ChangePhotoScreenEvent : UiEvent{
    data class GetAvatarFromPersonalData(val avatar: String?) : ChangePhotoScreenEvent
    data object OnBackFromChangePhotoClick : ChangePhotoScreenEvent
}
