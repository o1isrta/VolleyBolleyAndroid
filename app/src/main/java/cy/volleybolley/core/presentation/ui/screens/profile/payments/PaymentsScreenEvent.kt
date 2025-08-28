package cy.volleybolley.core.presentation.ui.screens.profile.payments

import cy.volleybolley.core.presentation.base.UiEvent

sealed interface PaymentsScreenEvent : UiEvent {
    data object ClickOnBackFromPayments : PaymentsScreenEvent
    data object ClickOnThaiBank : PaymentsScreenEvent
    data object ClickOnRevolut : PaymentsScreenEvent
    data object ClickOnThaiBankCheckbox : PaymentsScreenEvent
    data object ClickOnCashCheckbox : PaymentsScreenEvent
    data object ClickOnRevolutCheckbox : PaymentsScreenEvent
}
