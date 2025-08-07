package cy.volleybolley.profile.domain.model

data class Payment(
    val type: PaymentType,
    val account: String,
    val isPreferred: Boolean,
)
