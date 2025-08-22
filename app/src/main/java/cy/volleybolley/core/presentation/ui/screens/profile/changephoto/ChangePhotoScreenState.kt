package cy.volleybolley.core.presentation.ui.screens.profile.changephoto

import cy.volleybolley.core.presentation.base.UiState

data class ChangePhotoScreenState(
    val avatarUrl: String? = null,
    val buttonEnabled: Boolean = false,
) : UiState
