package cy.volleybolley.profile.presentation.ui.screens.enterpaymentdata

import cy.volleybolley.core.presentation.base.UiState

data class EnterPaymentDataScreenState(
    val accountValue: String = "",
    val buttonEnabled: Boolean = false,
) : UiState
