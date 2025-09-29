package cy.volleybolley.core.presentation.ui.screens.authorization.authorizationByPhone.sendCode.presentation.model

import cy.volleybolley.core.presentation.base.UiEvent

sealed interface AuthorizationByPhoneEvent : UiEvent {
    class TypePhoneNumber(val text: String) : AuthorizationByPhoneEvent
    object SendCodeButtonClicked : AuthorizationByPhoneEvent
}
