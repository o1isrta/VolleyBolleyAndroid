package cy.volleybolley.profile.data.dto

import cy.volleybolley.profile.domain.model.Payment
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaymentsUpdateBodyDto(
    @SerialName("payments") val payments: List<PaymentDto>
)

fun List<Payment>.toUpdateBody(): PaymentsUpdateBodyDto {
    return PaymentsUpdateBodyDto(
        payments = this.toDto()
    )
}
