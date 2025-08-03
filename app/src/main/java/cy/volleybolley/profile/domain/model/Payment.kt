package cy.volleybolley.profile.domain.model

data class Payment(
    val type: String,
    val account: String,
    val isPreferred: Boolean,
)
