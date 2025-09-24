package cy.volleybolley.core.presentation.ui.screens.authorization.signupByPhone.sendCode.presentation.model

import cy.volleybolley.core.presentation.base.UiEffect

sealed interface SignupByPhoneEffect : UiEffect {
    object NavigateToVerifyPhoneScreen : SignupByPhoneEffect
}
