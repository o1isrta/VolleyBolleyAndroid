package cy.volleybolley.auth.ui.screens.authorization

import cy.volleybolley.core.presentation.base.UiState

data class AuthorizationState(
    val isGoogleLoading: Boolean = false
) : UiState
