package cy.volleybolley.profile.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Payment(
    val type: PaymentType,
    val account: String,
    val isPreferred: Boolean,
)
