package cy.volleybolley.profile.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaymentDto(
    @SerialName("payment_type") val type: String,
    @SerialName("payment_account") val account: String,
    @SerialName("is_preferred") val isPreferred: Boolean,
)
