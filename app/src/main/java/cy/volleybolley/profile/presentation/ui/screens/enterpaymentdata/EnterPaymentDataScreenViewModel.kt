package cy.volleybolley.profile.presentation.ui.screens.enterpaymentdata

import cy.volleybolley.core.presentation.base.BaseViewModel
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
) {
    private val originPayments: List<Payment> = json.decodeFromString(paymentsJsonStringFromPaymentsScreen)
    val originPaymentType: PaymentType = PaymentType.Companion.findByName(paymentTypeName)

    private var savedAccountValue = getCorrectAccountValue(
        value = originPayments.find { it.type == originPaymentType }?.account ?: "",
        paymentType = originPaymentType
    )

    private var savedPaymentsJsonString: String? = null
    override val tag: String = TAG

    init {
        uiStateMutable.update {
            it.copy(accountValue = savedAccountValue)
        }
    }

    override fun obtainEvent(event: EnterPaymentDataScreenEvent) {
        when (event) {
            EnterPaymentDataScreenEvent.ClickOnBackFromEnterPaymentData -> {
                sendUiEffect(EnterPaymentDataScreenEffect.NavigateFromEnterPaymentDataScreen(savedPaymentsJsonString))
            }

            is EnterPaymentDataScreenEvent.AccountTextChanged -> {
                uiStateMutable.update { checkStateForButtonEnabled(event.text) }
            }

            EnterPaymentDataScreenEvent.OnSaveButtonClick -> {
                launchSafe(getErrorLogMessage = { "EnterPaymentDataScreen >> Save button: ${it.message}" }) {
                    val newPayment = Payment(
                        type = originPaymentType,
                        account = setCorrectAccountValue(uiState.value.accountValue.trim(), originPaymentType),
                        isPreferred = false
                    )

                    val sameTypePaymentInOrigins = originPayments.find { it.type == newPayment.type }
                    val updatedPayments = sameTypePaymentInOrigins?.let {
                        originPayments.map {
                            if (it.type == newPayment.type) newPayment.copy(isPreferred = it.isPreferred) else it
                        }
                    } ?: originPaymentsWithNewOne(newPayment)
                    savedPaymentsJsonString = json.encodeToString(updatedPayments)
                    // here we must do updatePaymentsUseCase.execute(updatedPayments) >> on success actions below:

                    savedAccountValue = uiState.value.accountValue.trim()
                    uiStateMutable.update { checkStateForButtonEnabled(savedAccountValue) }
                    sendUiEffect(
                        EnterPaymentDataScreenEffect.ShowInfoDialog(
                            onDoneButtonClick = {
                                sendUiEffect(
                                    EnterPaymentDataScreenEffect.NavigateFromEnterPaymentDataScreen(
                                        savedPaymentsJsonString
                                    )
                                )
                            }
                        )
                    )
                }
            }
        }
    }

    private fun originPaymentsWithNewOne(newPayment: Payment): List<Payment> = originPayments + newPayment

    private fun checkStateForButtonEnabled(newAccount: String): EnterPaymentDataScreenState {
        return EnterPaymentDataScreenState(
            accountValue = newAccount,
            buttonEnabled = newAccount.isNotBlank() && newAccount != savedAccountValue
        )
    }

    private fun getCorrectAccountValue(value: String, paymentType: PaymentType): String {
        return when {
            value.isEmpty() -> value
            paymentType == PaymentType.REVOLUT -> value.drop(1)
            else -> value
        }
    }

    private fun setCorrectAccountValue(value: String, paymentType: PaymentType): String {
        return if (paymentType == PaymentType.REVOLUT) "@$value" else value
    }

    companion object {
        val TAG = EnterPaymentDataScreenViewModel::class.simpleName ?: "EnterPaymentDataScreenViewModel"
    }
}
