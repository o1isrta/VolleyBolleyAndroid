package cy.volleybolley.referencedata.domain.api

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.referencedata.domain.model.Currency
import kotlinx.coroutines.flow.Flow

interface GetCurrenciesUseCase {
    fun execute(): Flow<VolleyResult<List<Currency>, ErrorType>>
}
