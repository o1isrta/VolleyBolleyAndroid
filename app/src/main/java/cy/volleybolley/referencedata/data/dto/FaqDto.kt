package cy.volleybolley.referencedata.data.dto

import cy.volleybolley.referencedata.domain.model.Faq
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FaqDto(
    @SerialName("faq")
    val faq: String
)

fun FaqDto.mapToDomain(): Faq {
    return Faq(
        faq = this.faq
    )
}
