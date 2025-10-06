package cy.volleybolley.core.presentation.ui.screens.authorization.authorization

import android.content.IntentSender
import cy.volleybolley.auth.data.UserDto
import cy.volleybolley.core.presentation.base.UiEffect

sealed class AuthorizationEffect : UiEffect {
    data class NavigateToRegistration(val user: String) : AuthorizationEffect()
    data class LaunchGoogleSignIn(val intentSender: IntentSender) : AuthorizationEffect()
    data class ShowError(val message: String) : AuthorizationEffect()
}
