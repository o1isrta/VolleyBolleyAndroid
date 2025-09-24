package cy.volleybolley.core.presentation.ui.screens.authorization.signupByPhone.sendCode.presentation.model

import cy.volleybolley.core.presentation.base.UiEvent

sealed interface SignupByPhoneEvent : UiEvent {
    class TypePhoneNumber(val text: String) : SignupByPhoneEvent
    object SendCodeButtonClicked : SignupByPhoneEvent
}
