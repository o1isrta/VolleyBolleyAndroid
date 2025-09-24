package cy.volleybolley.core.presentation.ui.screens.authorization.signupByPhone.verifyCode.presentation.model

import cy.volleybolley.core.presentation.base.UiEffect

sealed interface VerifyPhoneNumberEffect : UiEffect {
    object NavigateToRegistrationScreen : VerifyPhoneNumberEffect
}
