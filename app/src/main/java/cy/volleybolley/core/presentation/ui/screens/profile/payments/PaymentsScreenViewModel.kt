package cy.volleybolley.core.presentation.ui.screens.profile.payments

import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.navigation.EnterPaymentDataRoute
import cy.volleybolley.core.presentation.ui.screens.profile.payments.PaymentsScreenEffect.NavigateFromPaymentsScreen
import cy.volleybolley.core.presentation.ui.screens.profile.payments.PaymentsScreenEvent.ClickOnBackFromPayments
import cy.volleybolley.core.presentation.ui.screens.profile.payments.PaymentsScreenEvent.ClickOnPaymentsItem
import cy.volleybolley.core.presentation.ui.screens.profile.payments.PaymentsScreenEvent.ClickOnPaymentsItemCheckBox
import cy.volleybolley.profile.domain.GetPaymentsUseCase
import cy.volleybolley.profile.domain.UpdatePaymentsUseCase
import cy.volleybolley.profile.domain.model.Payment
import cy.volleybolley.profile.domain.model.PaymentType
import kotlinx.coroutines.flow.update
import kotlinx.serialization.json.Json

class PaymentsScreenViewModel(
    private val getPaymentsUseCase: GetPaymentsUseCase,
    private val updatePaymentsUseCase: UpdatePaymentsUseCase,
    private val json: Json,
) : BaseViewModel<PaymentsScreenState, PaymentsScreenEvent, PaymentsScreenEffect>(
    initialState = PaymentsScreenState(),
){
    private val mockPayments: List<Payment> = listOf(
        Payment(
            type = PaymentType.THAIBANK,
            account = "000 000 0000",
            isPreferred = false
        ),
        Payment(
            type = PaymentType.CASH,
            account = "",
            isPreferred = true
        ),
//        Payment(
//            type = PaymentType.REVOLUT,
//            account = "@AnonymousNemislimus",
//            isPreferred = false
//        )
    )

    init {
//        getPayments()  >> execute getPaymentsUseCase
        _uiState.update { it.copy(payments = mockPayments) }
    }
    override val tag: String = TAG

    override fun obtainEvent(event: PaymentsScreenEvent) {
        when(event) {
            ClickOnBackFromPayments -> sendUiEffect(NavigateFromPaymentsScreen(null))

            is ClickOnPaymentsItem -> {
                when(event.itemType) {
                    PaymentType.REVOLUT, PaymentType.THAIBANK  -> {
                        sendUiEffect(
                            NavigateFromPaymentsScreen(
                                EnterPaymentDataRoute(
                                    type = event.itemType,
                                    paymentsJsonString = json.encodeToString(uiState.value.payments),
                                )
                            )
                        )
                    }
                    else -> {}
                }
            }

            is ClickOnPaymentsItemCheckBox -> {
                // Execute updatePaymentsUseCase and update local state on success
                _uiState.update { it.copy(changePreferred(it.payments, event.itemType)) }
            }
        }
    }

    private fun changePreferred(oldPayments: List<Payment>, chosenType: PaymentType): List<Payment> {
        return oldPayments.map { payment ->
            payment.copy(isPreferred = payment.type == chosenType)
        }
    }

    companion object {
        val TAG = PaymentsScreenViewModel::class.simpleName ?: "PaymentsScreenViewModel"
    }
}
