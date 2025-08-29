package cy.volleybolley.profile.domain.model

enum class PaymentType(val nameValue: String) {
    REVOLUT("REVOLUT"),
    CASH("CASH"),
    THAIBANK("THAIBANK"),
    UNKNOWN("UNKNOWN");

    fun getSimpleName(): String =
        when (this) {
            THAIBANK -> "Thai bank"
            CASH -> "Cash"
            REVOLUT -> "Revolut"
            UNKNOWN -> "Unknown"
        }

    companion object {
        @JvmStatic
        fun findByName(paymentName: String): PaymentType {
            return entries.find { it.nameValue == paymentName } ?: UNKNOWN
        }
    }
}
