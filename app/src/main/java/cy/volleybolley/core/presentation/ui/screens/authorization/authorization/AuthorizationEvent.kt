package cy.volleybolley.core.presentation.ui.screens.authorization.authorization

import cy.volleybolley.core.presentation.base.UiEvent

sealed class AuthorizationEvent : UiEvent {
    object ContinueWithPhoneClicked : AuthorizationEvent()
    object ContinueWithGoogleClicked : AuthorizationEvent()
    object ContinueWithFacebookClicked : AuthorizationEvent()
}
