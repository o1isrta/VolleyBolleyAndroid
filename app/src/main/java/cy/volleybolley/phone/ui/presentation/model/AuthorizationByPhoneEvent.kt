package cy.volleybolley.phone.ui.presentation.model

import cy.volleybolley.core.presentation.base.UiEvent

sealed interface AuthorizationByPhoneEvent : UiEvent {
    data class TypePhone(val phone: String) : AuthorizationByPhoneEvent
    data class TypeCode(val code: String) : AuthorizationByPhoneEvent

    object SendCodeClicked : AuthorizationByPhoneEvent
    object ResendCodeClicked : AuthorizationByPhoneEvent
    object VerifyCodeClicked : AuthorizationByPhoneEvent
}
