package cy.volleybolley.core.presentation.ui.screens.profile.enterpaymentdata

import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.screens.profile.enterpaymentdata.EnterPaymentDataScreenEffect.NavigateFromEnterPaymentDataScreen
import cy.volleybolley.profile.domain.UpdatePaymentsUseCase
import cy.volleybolley.profile.domain.model.Payment
import cy.volleybolley.profile.domain.model.PaymentType
import kotlinx.coroutines.flow.update
import kotlinx.serialization.json.Json

class EnterPaymentDataScreenViewModel(
    private val updatePaymentsUseCase: UpdatePaymentsUseCase,
    private val json: Json,
    paymentTypeName: String,
    paymentsJsonStringFromPaymentsScreen: String,
) : BaseViewModel<EnterPaymentDataScreenState, EnterPaymentDataScreenEvent, EnterPaymentDataScreenEffect>(
    initialState = EnterPaymentDataScreenState()
){
    private val originPayments: List<Payment> = json.decodeFromString(paymentsJsonStringFromPaymentsScreen)
    val originPaymentType: PaymentType = PaymentType.findByName(paymentTypeName)
    override val tag: String = TAG

    init {
        _uiState.update {
            val incomingPayment = originPayments.find { it.type == originPaymentType }
            val incomingAccountValue = incomingPayment?.account ?: ""
            it.copy(accountValue = incomingAccountValue)
        }
    }

    override fun obtainEvent(event: EnterPaymentDataScreenEvent) {
        when(event) {
            is EnterPaymentDataScreenEvent.AccountTextChanged -> {
                _uiState.update { it.copy(accountValue = event.text) }
            }
            EnterPaymentDataScreenEvent.ClickOnBackFromEnterPaymentData -> {
                sendUiEffect(NavigateFromEnterPaymentDataScreen(null))
            }
            EnterPaymentDataScreenEvent.OnSaveButtonClick -> TODO()
        }
    }

    companion object {
        val TAG = EnterPaymentDataScreenViewModel::class.simpleName ?: "EnterPaymentDataScreenViewModel"
    }
}
