package cy.volleybolley.profile.presentation.ui.screens.changephoto

import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.profile.domain.DeleteAvatarUseCase
import cy.volleybolley.profile.domain.UpdateAvatarUseCase
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
            is ChangePhotoScreenEvent.GetAvatarFromPersonalData -> {
                originAvatar = event.avatar
                uiStateMutable.update { it.copy(avatarUrl = event.avatar) }
            }

            ChangePhotoScreenEvent.OnBackFromChangePhotoClick -> sendUiEffect(
                ChangePhotoScreenEffect.NavigateFromChangePhotoScreen(
                    null
                )
            )

            is ChangePhotoScreenEvent.OnGalleryPhotoSelect -> {
                uiStateMutable.update { checkStateForButtonEnabled(event.uriString) }
            }

            is ChangePhotoScreenEvent.OnCameraPhotoCreate -> {
                uiStateMutable.update { checkStateForButtonEnabled(event.photoUri) }
            }

            ChangePhotoScreenEvent.OnDeletePhotoClick -> {
                uiStateMutable.update { checkStateForButtonEnabled(null) }
            }

            ChangePhotoScreenEvent.OnSaveButtonClick -> {
                val newAvatar = uiState.value.avatarUrl ?: ""
                sendUiEffect(ChangePhotoScreenEffect.NavigateFromChangePhotoScreen(newAvatar))
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
