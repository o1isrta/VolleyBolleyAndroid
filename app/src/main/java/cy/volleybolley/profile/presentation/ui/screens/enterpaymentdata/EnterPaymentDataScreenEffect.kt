package cy.volleybolley.profile.presentation.ui.screens.enterpaymentdata

import cy.volleybolley.core.presentation.base.UiEffect

sealed interface EnterPaymentDataScreenEffect : UiEffect {
    data class NavigateFromEnterPaymentDataScreen(val updatedPaymentsJsonString: String?) : EnterPaymentDataScreenEffect

    data class ShowInfoDialog(
        val onDoneButtonClick: () -> Unit,
    ) : EnterPaymentDataScreenEffect
}
