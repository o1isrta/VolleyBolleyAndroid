package cy.volleybolley.phone.ui.presentation.model

import cy.volleybolley.core.presentation.base.UiEffect
import cy.volleybolley.phone.domain.ResendCodeToken

sealed interface AuthorizationByPhoneEffect : UiEffect {
    data class RequestSendCode(
        val phone: String,
        val resendToken: ResendCodeToken?
    ) : AuthorizationByPhoneEffect

    data class RequestVerifyCode(
        val verificationId: String,
        val code: String
    ) : AuthorizationByPhoneEffect

    data class NavigateToRegistration(val userJson: String) : AuthorizationByPhoneEffect
    object NavigateHome : AuthorizationByPhoneEffect
    data class ShowError(val message: String) : AuthorizationByPhoneEffect
}
