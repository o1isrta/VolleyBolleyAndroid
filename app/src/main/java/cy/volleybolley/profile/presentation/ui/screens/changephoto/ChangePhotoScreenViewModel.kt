package cy.volleybolley.profile.presentation.ui.screens.changephoto

import android.net.Uri
import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.profile.domain.DeleteAvatarUseCase
import cy.volleybolley.profile.domain.UpdateAvatarUseCase
import cy.volleybolley.profile.presentation.ui.screens.changephoto.ChangePhotoScreenEffect.NavigateFromChangePhotoScreen
import cy.volleybolley.profile.presentation.ui.screens.changephoto.ChangePhotoScreenEvent.GetAvatarFromPersonalData
import cy.volleybolley.profile.presentation.ui.screens.changephoto.ChangePhotoScreenEvent.OnBackFromChangePhotoClick
import cy.volleybolley.profile.presentation.ui.screens.changephoto.ChangePhotoScreenEvent.OnCameraPhotoCreate
import cy.volleybolley.profile.presentation.ui.screens.changephoto.ChangePhotoScreenEvent.OnDeletePhotoClick
import cy.volleybolley.profile.presentation.ui.screens.changephoto.ChangePhotoScreenEvent.OnGalleryPhotoSelect
import cy.volleybolley.profile.presentation.ui.screens.changephoto.ChangePhotoScreenEvent.OnSaveButtonClick
import kotlinx.coroutines.flow.update

class ChangePhotoScreenViewModel(
    private val updateAvatarUseCase: UpdateAvatarUseCase,
    private val deleteAvatarUseCase: DeleteAvatarUseCase,
) : BaseViewModel<ChangePhotoScreenState, ChangePhotoScreenEvent, ChangePhotoScreenEffect>(
    initialState = ChangePhotoScreenState()
) {
    private var originAvatarString: String? = uiState.value.avatarUrl
    private var chosenImageByteArray: ByteArray? = null

    override fun obtainEvent(event: ChangePhotoScreenEvent) {
        when (event) {
            is GetAvatarFromPersonalData -> {
                originAvatarString = event.avatarUrl
                uiStateMutable.update { it.copy(avatarUrl = event.avatarUrl) }
            }

            OnBackFromChangePhotoClick -> sendUiEffect(
                NavigateFromChangePhotoScreen(null)
            )

            is OnGalleryPhotoSelect -> {
                chosenImageByteArray = event.pictureBytes
                uiStateMutable.update { checkStateForButtonEnabled(event.pictureUri) }
            }

            is OnCameraPhotoCreate -> {
                chosenImageByteArray = event.photoBytes
                uiStateMutable.update { checkStateForButtonEnabled(event.photoUri) }
            }

            OnDeletePhotoClick -> {
                chosenImageByteArray = null
                uiStateMutable.update { checkStateForButtonEnabled(null) }
            }

            OnSaveButtonClick -> {
                /* Here we`ll use chosenImageByteArray as argument for updateAvatarUseCase.
                We will replace newAvatar with a boolean flag that will determine
                whether to update the avatar in PersonalData or not. */
                val newAvatar = uiState.value.avatarUrl ?: ""
                sendUiEffect(NavigateFromChangePhotoScreen(newAvatar))
            }
        }
    }

    private fun checkStateForButtonEnabled(newAvatarUri: Uri?): ChangePhotoScreenState {
        val newAvatarString = newAvatarUri?.toString()
        return ChangePhotoScreenState(
            avatarUrl = newAvatarString,
            buttonEnabled = newAvatarString != originAvatarString
        )
    }
}
