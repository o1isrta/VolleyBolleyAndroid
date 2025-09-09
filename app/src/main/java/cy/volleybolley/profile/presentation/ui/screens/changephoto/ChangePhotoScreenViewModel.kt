package cy.volleybolley.profile.presentation.ui.screens.changephoto

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
    private var originAvatar: String? = uiState.value.avatarUrl

    override val tag: String = TAG

    override fun obtainEvent(event: ChangePhotoScreenEvent) {
        when (event) {
            is GetAvatarFromPersonalData -> {
                originAvatar = event.avatar
                uiStateMutable.update { it.copy(avatarUrl = event.avatar) }
            }

            OnBackFromChangePhotoClick -> sendUiEffect(
                NavigateFromChangePhotoScreen(null)
            )

            is OnGalleryPhotoSelect -> {
                uiStateMutable.update { checkStateForButtonEnabled(event.uriString) }
            }

            is OnCameraPhotoCreate -> {
                uiStateMutable.update { checkStateForButtonEnabled(event.photoUri) }
            }

            OnDeletePhotoClick -> {
                uiStateMutable.update { checkStateForButtonEnabled(null) }
            }

            OnSaveButtonClick -> {
                val newAvatar = uiState.value.avatarUrl ?: ""
                sendUiEffect(NavigateFromChangePhotoScreen(newAvatar))
            }
        }
    }

    private fun checkStateForButtonEnabled(newAvatar: String?): ChangePhotoScreenState {
        return ChangePhotoScreenState(
            avatarUrl = newAvatar,
            buttonEnabled = newAvatar != originAvatar
        )
    }

    companion object {
        val TAG = ChangePhotoScreenViewModel::class.simpleName ?: "ChangePhotoScreenViewModel"
    }
}
