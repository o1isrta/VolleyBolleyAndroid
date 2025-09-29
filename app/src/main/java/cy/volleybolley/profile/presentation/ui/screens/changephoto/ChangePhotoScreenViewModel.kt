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

    override val tag: String = ChangePhotoScreenViewModel::class.simpleName ?: "ChangePhotoScreenViewModel"

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
                uiStateMutable.update { checkStateForButtonEnabled(event.pictureUri, event.pictureBytes) }
            }

            is OnCameraPhotoCreate -> {
                uiStateMutable.update { checkStateForButtonEnabled(event.photoUri, event.photoBytes) }
            }

            OnDeletePhotoClick -> {
                uiStateMutable.update { checkStateForButtonEnabled(null, null) }
            }

            OnSaveButtonClick -> {
                val newAvatar = uiState.value.avatarUrl ?: ""
                sendUiEffect(NavigateFromChangePhotoScreen(newAvatar))
            }
        }
    }

    private fun checkStateForButtonEnabled(newAvatarUri: Uri?, newBytes: ByteArray?): ChangePhotoScreenState {
        val newAvatarString = newAvatarUri?.toString()
        return ChangePhotoScreenState(
            avatarUrl = newAvatarString,
            avatarBytes = newBytes,
            buttonEnabled = newAvatarString != originAvatarString
        )
    }
}
