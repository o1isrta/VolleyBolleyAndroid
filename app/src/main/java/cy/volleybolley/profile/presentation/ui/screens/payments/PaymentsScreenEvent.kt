package cy.volleybolley.profile.presentation.ui.screens.payments

import cy.volleybolley.core.presentation.base.UiEvent
import cy.volleybolley.profile.domain.model.PaymentType

sealed interface PaymentsScreenEvent : UiEvent {
    data object ClickOnBackFromPayments : PaymentsScreenEvent
    data class ClickOnPaymentsItem(val itemType: PaymentType) : PaymentsScreenEvent
    data class ClickOnPaymentsItemCheckBox(val itemType: PaymentType) : PaymentsScreenEvent
}
