package cy.volleybolley.core.presentation.ui.screens.authorization.authorization

import cy.volleybolley.core.presentation.base.UiEffect

sealed class AuthorizationEffect : UiEffect {
    data class NavigateToRegistration(val user: String) : AuthorizationEffect()
    data object LaunchGoogleSignIn : AuthorizationEffect()
    data class ShowToast(val message: String) : AuthorizationEffect()
}
