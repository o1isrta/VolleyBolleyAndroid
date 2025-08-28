package cy.volleybolley.core.presentation.ui.screens.profile.enterpaymentdata

import cy.volleybolley.core.presentation.base.UiEffect
import cy.volleybolley.core.presentation.ui.navigation.NavMap

sealed interface EnterPaymentDataScreenEffect : UiEffect {
    data class NavigateFromEnterPaymentDataScreen(val updatedPaymentsJsonString: String?) : EnterPaymentDataScreenEffect

    data class ShowInfoDialog(
        val onDoneButtonClick: () -> Unit,
    ) : EnterPaymentDataScreenEffect
}
