package cy.volleybolley.profile.domain.model

enum class PaymentType(val nameValue: String) {
    REVOLUT("REVOLUTE"),
    THAIBANK("THAIBANK"),
    CASH("CASH"),
    UNKNOWN("UNKNOWN")
}
