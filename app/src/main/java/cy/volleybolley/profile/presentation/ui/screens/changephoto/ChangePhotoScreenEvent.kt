package cy.volleybolley.profile.presentation.ui.screens.changephoto

import android.net.Uri
import cy.volleybolley.core.presentation.base.UiEvent

sealed interface ChangePhotoScreenEvent : UiEvent {
    data class GetAvatarFromPersonalData(val avatarUrl: String?) : ChangePhotoScreenEvent

    data object OnBackFromChangePhotoClick : ChangePhotoScreenEvent

    data class OnGalleryPhotoSelect(val pictureUri: Uri, val pictureBytes: ByteArray?) : ChangePhotoScreenEvent {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as OnGalleryPhotoSelect

            return pictureBytes.contentEquals(other.pictureBytes)
        }

        override fun hashCode(): Int {
            return pictureBytes?.contentHashCode() ?: 0
        }
    }

    data class OnCameraPhotoCreate(val photoUri: Uri, val photoBytes: ByteArray?) : ChangePhotoScreenEvent {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as OnCameraPhotoCreate

            return photoBytes.contentEquals(other.photoBytes)
        }

        override fun hashCode(): Int {
            return photoBytes?.contentHashCode() ?: 0
        }
    }

    data object OnDeletePhotoClick : ChangePhotoScreenEvent

    data object OnSaveButtonClick : ChangePhotoScreenEvent
}
