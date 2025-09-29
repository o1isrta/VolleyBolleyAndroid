package cy.volleybolley.core.presentation.ui.screens.authorization.authorizationByPhone.verifyCode.presentation.model

import cy.volleybolley.core.presentation.base.UiEvent

sealed interface VerifyPhoneNumberEvent : UiEvent {
    class TypeCode(val text: String) : VerifyPhoneNumberEvent
    object VerifyCodeButtonClicked : VerifyPhoneNumberEvent
    object SendNewCodeButtonClicked : VerifyPhoneNumberEvent
}
