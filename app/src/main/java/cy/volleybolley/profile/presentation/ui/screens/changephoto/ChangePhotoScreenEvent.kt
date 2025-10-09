package cy.volleybolley.profile.presentation.ui.screens.changephoto

import android.net.Uri
import cy.volleybolley.core.presentation.base.UiEvent

sealed interface ChangePhotoScreenEvent : UiEvent {
    data class GetAvatarFromPersonalData(val avatarUrl: String?) : ChangePhotoScreenEvent
    data object OnBackFromChangePhotoClick : ChangePhotoScreenEvent
    class OnGalleryPhotoSelect(val pictureUri: Uri, val pictureBytes: ByteArray?) : ChangePhotoScreenEvent
    class OnCameraPhotoCreate(val photoUri: Uri, val photoBytes: ByteArray?) : ChangePhotoScreenEvent
    data object OnDeletePhotoClick : ChangePhotoScreenEvent
    data object OnSaveButtonClick : ChangePhotoScreenEvent
}
