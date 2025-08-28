package cy.volleybolley.core.presentation.ui.screens.profile.payments

import cy.volleybolley.core.presentation.base.BaseViewModel

class PaymentsScreenViewModel() : BaseViewModel<PaymentsScreenState, PaymentsScreenEvent, PaymentsScreenEffect>(
    initialState = PaymentsScreenState(),
){
    override val tag: String = TAG

    override fun obtainEvent(event: PaymentsScreenEvent) {

    }

    companion object {
        val TAG = PaymentsScreenViewModel::class.simpleName ?: "PaymentsScreenViewModel"
    }
}
