package cy.volleybolley.referencedata.domain.api

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.referencedata.domain.model.Faq

interface GetFaqUseCase {
    suspend fun execute(): VolleyResult<Faq, ErrorType>
}
