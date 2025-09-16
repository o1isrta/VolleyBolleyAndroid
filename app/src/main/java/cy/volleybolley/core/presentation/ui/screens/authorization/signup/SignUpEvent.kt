package cy.volleybolley.core.presentation.ui.screens.authorization.signup

import cy.volleybolley.core.presentation.base.UiEvent

sealed class SignUpEvent : UiEvent {
    object ContinueWithPhoneClicked : SignUpEvent()
    object ContinueWithGoogleClicked : SignUpEvent()
    object ContinueWithFacebookClicked : SignUpEvent()
}

