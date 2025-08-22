package cy.volleybolley.core.presentation.ui.screens.profile.changephoto

import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.screens.profile.changephoto.ChangePhotoScreenEffect.NavigateFromChangePhotoScreen
import cy.volleybolley.core.presentation.ui.screens.profile.changephoto.ChangePhotoScreenEvent.GetAvatarFromPersonalData
import cy.volleybolley.core.presentation.ui.screens.profile.changephoto.ChangePhotoScreenEvent.OnBackFromChangePhotoClick
import cy.volleybolley.core.presentation.ui.screens.profile.changephoto.ChangePhotoScreenEvent.OnCameraPhotoClick
import cy.volleybolley.core.presentation.ui.screens.profile.changephoto.ChangePhotoScreenEvent.OnDeletePhotoClick
import cy.volleybolley.core.presentation.ui.screens.profile.changephoto.ChangePhotoScreenEvent.OnGalleryPhotoClick
import cy.volleybolley.core.presentation.ui.screens.profile.changephoto.ChangePhotoScreenEvent.OnSaveButtonClick
import cy.volleybolley.profile.domain.DeleteAvatarUseCase
import cy.volleybolley.profile.domain.UpdateAvatarUseCase
import kotlinx.coroutines.flow.update

class ChangePhotoScreenViewModel(
    private val updateAvatarUseCase: UpdateAvatarUseCase,
    private val deleteAvatarUseCase: DeleteAvatarUseCase,
) : BaseViewModel<ChangePhotoScreenState, ChangePhotoScreenEvent, ChangePhotoScreenEffect>(
    initialState = ChangePhotoScreenState()
){
    private var originAvatar: String? = uiState.value.avatarUrl

    override val tag: String = TAG

    override fun obtainEvent(event: ChangePhotoScreenEvent) {
        when(event) {
            is GetAvatarFromPersonalData -> {
                originAvatar = event.avatar
                _uiState.update { it.copy(avatarUrl = event.avatar) }
            }

            OnBackFromChangePhotoClick -> sendUiEffect(NavigateFromChangePhotoScreen(null))

            OnGalleryPhotoClick -> {}

            OnCameraPhotoClick -> {}

            OnDeletePhotoClick -> {}

            OnSaveButtonClick -> {}
        }
    }

    private fun checkStateForButtonEnabled(newState: ChangePhotoScreenState): ChangePhotoScreenState {
        return if (newState.avatarUrl == originAvatar) {
            newState.copy(buttonEnabled = false)
        }  else {
            newState.copy(buttonEnabled = true)
        }
    }

    companion object {
        val TAG = ChangePhotoScreenViewModel::class.simpleName ?: "ChangePhotoScreenViewModel"
    }
}
