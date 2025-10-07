package cy.volleybolley.profile.presentation.ui.screens.changephoto

import cy.volleybolley.core.presentation.base.UiState

data class ChangePhotoScreenState(
    val avatarUrl: String? = null,
    val buttonEnabled: Boolean = false,
) : UiState
