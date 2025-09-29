package cy.volleybolley.core.presentation.ui.screens.authorization.authorizationByPhone.sendCode.presentation.model

import cy.volleybolley.core.presentation.base.UiEffect

sealed interface AuthorizationByPhoneEffect : UiEffect {
    object NavigateToVerifyPhoneScreen : AuthorizationByPhoneEffect
}
