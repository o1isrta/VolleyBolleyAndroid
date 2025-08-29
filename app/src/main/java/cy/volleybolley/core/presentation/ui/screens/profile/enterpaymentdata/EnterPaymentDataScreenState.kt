package cy.volleybolley.core.presentation.ui.screens.profile.enterpaymentdata

import cy.volleybolley.core.presentation.base.UiState

data class EnterPaymentDataScreenState(
    val accountValue: String = "",
    val buttonEnabled: Boolean = false,
) : UiState
