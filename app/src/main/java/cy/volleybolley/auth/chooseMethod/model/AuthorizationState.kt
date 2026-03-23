package cy.volleybolley.auth.chooseMethod.model

import cy.volleybolley.core.presentation.base.UiState

data class AuthorizationState(
    val isGoogleLoading: Boolean = false
) : UiState
