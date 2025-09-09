package cy.volleybolley.profile.presentation.ui.screens.changephoto

import cy.volleybolley.core.presentation.base.UiEvent

sealed interface ChangePhotoScreenEvent : UiEvent {
    data class GetAvatarFromPersonalData(val avatar: String?) : ChangePhotoScreenEvent
    data object OnBackFromChangePhotoClick : ChangePhotoScreenEvent
    data class OnGalleryPhotoSelect(val uriString: String) : ChangePhotoScreenEvent
    data class OnCameraPhotoCreate(val photoUri: String) : ChangePhotoScreenEvent
    data object OnDeletePhotoClick : ChangePhotoScreenEvent
    data object OnSaveButtonClick : ChangePhotoScreenEvent
}
