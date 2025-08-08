package cy.volleybolley.profile.domain.model

enum class PaymentType(val nameValue: String) {
    REVOLUT("REVOLUTE"),
    THAIBANK("THAIBANK"),
    CASH("CASH"),
    UNKNOWN("UNKNOWN");

    companion object {
        @JvmStatic
        fun findByName(paymentName: String): PaymentType {
            return entries.find { it.nameValue == paymentName } ?: UNKNOWN
        }
    }
}
