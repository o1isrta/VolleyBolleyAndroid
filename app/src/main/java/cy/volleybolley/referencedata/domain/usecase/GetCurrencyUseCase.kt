package cy.volleybolley.referencedata.domain.usecase

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.referencedata.domain.model.Currency
import kotlinx.coroutines.flow.Flow

interface GetCurrencyUseCase {
    fun execute(): Flow<VolleyResult<Currency, ErrorType>>
}
