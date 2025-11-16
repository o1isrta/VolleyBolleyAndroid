package cy.volleybolley.profile.presentation.ui.screens.payments.model

import androidx.lifecycle.SavedStateHandle

class BackPaymentsHolder(private val savedStateHandle: SavedStateHandle) {
    fun getPaymentsJsonString(): String? = savedStateHandle.get<String>(PAYMENTS_KEY)

    fun clearBackPayments() {
        savedStateHandle.remove<String>(PAYMENTS_KEY)
    }

    companion object {
        const val PAYMENTS_KEY = "payments_key"
    }
}
