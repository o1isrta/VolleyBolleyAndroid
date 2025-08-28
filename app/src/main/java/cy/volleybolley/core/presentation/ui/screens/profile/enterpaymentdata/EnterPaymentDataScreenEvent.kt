package cy.volleybolley.core.presentation.ui.screens.profile.enterpaymentdata

import cy.volleybolley.core.presentation.base.UiEvent

sealed interface EnterPaymentDataScreenEvent : UiEvent {
    data class AccountTextChanged(val text: String) : EnterPaymentDataScreenEvent
    data object OnSaveButtonClick : EnterPaymentDataScreenEvent
}
