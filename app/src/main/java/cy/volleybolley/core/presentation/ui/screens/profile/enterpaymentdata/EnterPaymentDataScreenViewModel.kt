package cy.volleybolley.core.presentation.ui.screens.profile.enterpaymentdata

import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.profile.domain.UpdatePaymentsUseCase
import kotlinx.serialization.json.Json

class EnterPaymentDataScreenViewModel(
    private val updatePaymentsUseCase: UpdatePaymentsUseCase,
    private val json: Json,
) : BaseViewModel<EnterPaymentDataScreenState, EnterPaymentDataScreenEvent, EnterPaymentDataScreenEffect>(
    initialState = EnterPaymentDataScreenState()
){
    override val tag: String = TAG

    override fun obtainEvent(event: EnterPaymentDataScreenEvent) {

    }

    companion object {
        val TAG = EnterPaymentDataScreenViewModel::class.simpleName ?: "EnterPaymentDataScreenViewModel"
    }
}
