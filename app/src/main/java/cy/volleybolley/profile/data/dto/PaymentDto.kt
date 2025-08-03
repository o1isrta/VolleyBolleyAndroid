package cy.volleybolley.profile.data.dto

import cy.volleybolley.profile.domain.model.Payment
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class PaymentDto(
    @SerialName("payment_type") val type: String,
    @SerialName("payment_account") val account: String,
    @SerialName("is_preferred") val isPreferred: Boolean,
)

fun List<PaymentDto>.toDomain(): List<Payment> = this.map { it.toDomain() }

fun PaymentDto.toDomain(): Payment {
    return Payment(
        type = type,
        account = account,
        isPreferred = isPreferred,
    )
}
