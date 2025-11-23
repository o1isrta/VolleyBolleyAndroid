package cy.volleybolley.auth.ui.screens.authorization

import cy.volleybolley.core.presentation.base.UiEvent

sealed class AuthorizationEvent : UiEvent {
    object ContinueWithGoogleClicked : AuthorizationEvent()
    object ContinueWithFacebookClicked : AuthorizationEvent()
    data class GoogleTokenReceived(val idToken: String?) : AuthorizationEvent()
    object GoogleSignInCancelled : AuthorizationEvent()
    object GoogleSignInFailed : AuthorizationEvent()
}
