package cy.volleybolley.referencedata.data.localdto

import cy.volleybolley.referencedata.domain.model.Faq
import kotlinx.serialization.Serializable

@Serializable
data class FaqLocalDto(
    val faq: String
)

fun FaqLocalDto.mapToDomain(): Faq {
    return Faq(
        faq = faq
    )
}

fun Faq.mapToLocalDto(): FaqLocalDto {
    return FaqLocalDto(
        faq = faq
    )
}
