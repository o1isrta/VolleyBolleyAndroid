package cy.volleybolley.referencedata.domain.api

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.referencedata.domain.model.Faq
import kotlinx.coroutines.flow.Flow

interface GetFaqUseCase {
    fun execute(): Flow<VolleyResult<Faq, ErrorType>>
}
