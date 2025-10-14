package cy.volleybolley.profile.presentation.ui.screens.payments

import cy.volleybolley.core.presentation.base.UiState
import cy.volleybolley.profile.domain.model.Payment
import cy.volleybolley.profile.domain.model.PaymentType

data class PaymentsScreenState(
    val payments: List<Payment> = listOf(
        Payment(
            type = PaymentType.CASH,
            account = "",
            isPreferred = true
        )
    )
) : UiState
