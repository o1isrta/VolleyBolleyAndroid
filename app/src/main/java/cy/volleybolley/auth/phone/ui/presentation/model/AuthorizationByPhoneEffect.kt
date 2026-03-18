package cy.volleybolley.auth.phone.ui.presentation.model

import cy.volleybolley.auth.phone.domain.ResendCodeToken
import cy.volleybolley.core.presentation.base.UiEffect

sealed interface AuthorizationByPhoneEffect : UiEffect {
    data class RequestSendCode(
        val phone: String,
        val resendToken: ResendCodeToken?
    ) : AuthorizationByPhoneEffect

    data class RequestVerifyCode(
        val verificationId: String,
        val code: String
    ) : AuthorizationByPhoneEffect
    object NavigateToRegistration : AuthorizationByPhoneEffect
    object NavigateHome : AuthorizationByPhoneEffect
    data class ShowError(val message: String) : AuthorizationByPhoneEffect
}
