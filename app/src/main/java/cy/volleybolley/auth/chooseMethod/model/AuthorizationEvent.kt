package cy.volleybolley.auth.chooseMethod.model

import cy.volleybolley.core.presentation.base.UiEvent

sealed interface AuthorizationEvent : UiEvent {
    object ContinueWithFacebookClicked : AuthorizationEvent
    object GoogleSignInStarted : AuthorizationEvent
    class GoogleTokenReceived(val googleIdToken: String) : AuthorizationEvent
    object GoogleSignInFailed : AuthorizationEvent
}
