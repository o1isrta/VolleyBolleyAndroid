package cy.volleybolley.profile.presentation.ui.screens.payments

import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.model.VolleyMocks
import cy.volleybolley.core.presentation.ui.navigation.EnterPaymentDataRoute
import cy.volleybolley.profile.domain.GetPaymentsUseCase
import cy.volleybolley.profile.domain.UpdatePaymentsUseCase
import cy.volleybolley.profile.domain.model.Payment
import cy.volleybolley.profile.domain.model.PaymentType
import cy.volleybolley.profile.presentation.ui.screens.payments.PaymentsScreenEffect.NavigateFromPaymentsScreen
import cy.volleybolley.profile.presentation.ui.screens.payments.PaymentsScreenEvent.ClickOnBackFromPayments
import cy.volleybolley.profile.presentation.ui.screens.payments.PaymentsScreenEvent.ClickOnPaymentsItem
import cy.volleybolley.profile.presentation.ui.screens.payments.PaymentsScreenEvent.ClickOnPaymentsItemCheckBox
import cy.volleybolley.profile.presentation.ui.screens.payments.model.BackPaymentsHolder
import kotlinx.coroutines.flow.update
import kotlinx.serialization.json.Json

class PaymentsScreenViewModel(
    private val backPaymentsHolder: BackPaymentsHolder,
    private val getPaymentsUseCase: GetPaymentsUseCase,
    private val updatePaymentsUseCase: UpdatePaymentsUseCase,
    private val json: Json,
) : BaseViewModel<PaymentsScreenState, PaymentsScreenEvent, PaymentsScreenEffect>(
    initialState = PaymentsScreenState(),
) {
    init {
//        getPayments()  >> execute getPaymentsUseCase
        uiStateMutable.update { it.copy(payments = VolleyMocks.mockPayments) }
    }

    override fun obtainEvent(event: PaymentsScreenEvent) {
        when (event) {
            ClickOnBackFromPayments -> sendUiEffect(
                NavigateFromPaymentsScreen(null)
            )

            is ClickOnPaymentsItem -> {
                when (event.itemType) {
                    PaymentType.REVOLUT, PaymentType.THAIBANK -> {
                        sendUiEffect(
                            NavigateFromPaymentsScreen(
                                EnterPaymentDataRoute(
                                    paymentTypeName = event.itemType.nameValue,
                                    paymentsJsonString = json.encodeToString(uiState.value.payments),
                                )
                            )
                        )
                    }

                    else -> Unit
                }
            }

            is ClickOnPaymentsItemCheckBox -> {
                // Execute updatePaymentsUseCase and update local state on success
                uiStateMutable.update { it.copy(changePreferred(it.payments, event.itemType)) }
            }
        }
    }

    fun handleBackPayments() {
        backPaymentsHolder.getPaymentsJsonString()?.let { jsonString ->
            uiStateMutable.update { it.copy(payments = json.decodeFromString<List<Payment>>(jsonString)) }
            backPaymentsHolder.clearBackPayments()
        }
    }

    private fun changePreferred(oldPayments: List<Payment>, chosenType: PaymentType): List<Payment> {
        return oldPayments.map { payment ->
            payment.copy(isPreferred = payment.type == chosenType)
        }
    }
}
