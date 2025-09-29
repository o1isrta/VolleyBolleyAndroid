package cy.volleybolley.profile.domain.model

import cy.volleybolley.R

enum class PaymentType(val nameValue: String) {
    REVOLUT("REVOLUT"),
    CASH("CASH"),
    THAIBANK("THAIBANK"),
    UNKNOWN("UNKNOWN");

    fun getSimpleName(): Int =
        when (this) {
            THAIBANK -> R.string.payment_type_thai_bank
            CASH -> R.string.payment_type_cash
            REVOLUT -> R.string.payment_type_revolut
            UNKNOWN -> R.string.payment_type_unknown
        }

    companion object {
        @JvmStatic
        fun findByName(paymentName: String): PaymentType {
            return entries.find { it.nameValue == paymentName } ?: UNKNOWN
        }
    }
}
